#!/bin/bash
# Author: Yuchen Sun
# Email: ysun@spinq.cn
# Description: This script is used to build the BAQIS Cloud Server
#
# To run the script, input in terminate as:
# bash build.sh <env> <backend_package_name> <backend_port> <build_prefix> <backend_encode_salt>
# After running the script, a build directory will be generated under the current path, with all you need for run the project in an online production environment in it
#
# <env> - the environment you want to build, the script will pack up the config files correlated to this environment for you
# <backend_package_name> - the jar package name
# <backend_port> - the port the server will run on
# <build_prefix> - the deployment directory prefix of the project
#                  the backend will call the simulator under <build_prefix>/simulator
#                  backend console/java-gc logs will be put under <build_prefix>/backend/logs
# <backend_encode_salt> - jaspyt salt used to decode properties such as database password
# <init_server_mem> - init server memory
# <max_server_mem> - max server memory
#
# bash build.sh uat gemini-lab-0.0.1.jar 4500 /home/spinq/gemini-lab-uat/backend spinQCloudApplication 2g 2g
env=${1}
backend_package_name=${2}
backend_port=${3}
build_prefix=${4}
backend_encode_salt=${5}
init_server_mem=${6}
max_server_mem=${7}

# input validator
if [ $# < 5 ]; then
    echo "[ERROR] No enough arguments. Please run script as bash build.sh <env> <backend_package_name> <backend_port> <build_prefix> <backend_encode_salt> <init_server_mem> <max_server_mem>"
    exit 1
fi
if [ "$env" != "sit" -a "$env" != "uat" -a "$env" != "prod" ]; then
    echo "[ERROR] Invalid build environment, please choose from sit/uat/prod"
    exit 2
fi
portWithoutNum=`echo $backend_port|sed 's/[0-9]//g'`
if [ $portWithoutNum ]; then
    echo "[ERROR] Port $backend_port is not Number"
    exit 3
fi
if [ ! -d $build_prefix ]; then
    echo "[ERROR] Prefix Directory $build_prefix does not exists"
    exit 4
fi

application_config_filename="application-$env.yml"
log_config_filename="log4j2-$env.xml"

# build the project
mvn clean install -Dmaven.test.skip=true
if [ $? -ne 0 ]; then 
    echo "[ERROR] Maven install failed"
    exit 6
fi

if [ -d "./build" ]; then rm -r "./build"; fi
mkdir "./build"

# copy resources
cp "./target/${backend_package_name}" ./build
cp "./src/main/resources/${application_config_filename}" ./build
cp "./src/main/resources/log4j2/${log_config_filename}" ./build
       
# generate start script
echo "
    zipFile() {
        compressed_filename=\"\$1.gz\"
        if [ -e \$compressed_filename ]
        then
            echo \"压缩文件存在，需要合并\"
            mv \$1 \"\$1.tmp\"
            gzip \"\$1.tmp\"
            zcat \$compressed_filename \"\$1.tmp.gz\" | gzip - > \"\$1.final.gz\"
            mv \"\$1.final.gz\" \$compressed_filename
            rm \"\$1.tmp.gz\"
        else
            echo \"压缩文件不存在，成功压缩\"
            gzip \$1
        fi
    }

    pid=\`lsof -i :$backend_port | grep 'java' | grep -v grep | awk '{print \$2}'\`
    if [ -n \"\$pid\" ]; then
        echo \"[INFO] Kill old server process \"\$pid\".\"
        kill -15 \$pid
    fi
    
    build_prefix=$build_prefix
    backend_log_repo=$build_prefix/logs
    # backup console logs and java garbage collection logs
    if [ ! -d \"\$backend_log_repo/console\" ]; then mkdir -p \"\$backend_log_repo/console\"; fi
    current=\`date \"+%Y-%m-%d_%H\"\`
    gc_backup=\"gc_\$current.log\"
    console_backup=\"console_\$current.log\"
    if [ -s \"\$backend_log_repo/console/console.log\" ]; then
        mv \"\$backend_log_repo/console/console.log\" \"\$backend_log_repo/console/\$console_backup\"
        zipInput=\"\$backend_log_repo/console/\$console_backup\"
        zipFile \$zipInput
    fi

    # start the new process
    nohup java -jar -Xms$init_server_mem -Xmx$max_server_mem -Djasypt.encryptor.password=$backend_encode_salt -Dspring.profiles.active=$env $backend_package_name > \$backend_log_repo/console/console.log 2>&1 &
    echo \"[INFO] Successfully restart java server on $backend_port.\"
" > "./build/start-$env.sh"

# generate daemon script
echo "
    #!/bin/bash
    # Author: Yuchen Sun
    # Email: ysun@spinq.cn
    # Description: This is a daemon script used to restart a server if it is accidentally shut down
    #              start.sh is your script used to start the server
    #
    # To start the daemon process on a running server and write to a log file, run the following command in terminal:
    # nohup sh <daemon-script-name> >> <log-address> 2>&1 &

    # To stop the daemon process, run the following command in terminal:
    # kill -15 \`ps -aux \| grep 'sh <daemon-script-name>
    # <daemon-script-name>: your script name, in this case 'daemon.sh'
    # <log-address>: address of the log file

    while :
    do
        pid=\`lsof -i :$backend_port | grep 'java' | grep -v grep | awk '{if ( NR == 1 ) print \$2}'\`
        current=\`date \"+%Y-%m-%d %H:%M:%S\"\` 
        if [ -n \"\$pid\" ]; then
            echo \"[INFO \$current] Gemini-pi $env server is alive, pid = \$pid !\"
            else
            echo \"[ALERT \$current] Gemini-pi $env server has was shutdown!\"
            sh start.sh
            echo \"[INFO \$current] Gemini-pi $env server has been restart!\"
        fi
    sleep 5
    done
" > "./build/daemon-$env.sh"

# generate consoleZip script
echo "
    #!/bin/bash
    # Author: Yuchen Sun
    # Email: ysun@spinq.cn
    # Description: This is a script used to compress console log periodically, it is called by crontab

    backend_log_repo=$build_prefix/logs
    current=\`date \"+%Y-%m-%d_%H\"\`
    console_backup=\"console_\$current.log\"
    cat \"\$backend_log_repo/console/console.log\" >> \"\$backend_log_repo/console/\$console_backup\"
    gzip  \"\$backend_log_repo/console/\$console_backup\"
" > "./build/consoleZip-$env.sh"