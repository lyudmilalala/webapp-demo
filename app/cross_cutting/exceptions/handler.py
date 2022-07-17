import json

def register_errors(app):
    @app.errorhandler(400)
    def bad_request(e):
        return json.dumps({"status": 1002, "message": "Bad Request"}), 400

    @app.errorhandler(404)
    def page_not_found(e):
        return json.dumps({"status": 1003, "message": "Page not found"}), 404

    @app.errorhandler(500)
    def internal_server_error(e):
        return json.dumps({"status": 1000, "message": "Server side error"}), 500
