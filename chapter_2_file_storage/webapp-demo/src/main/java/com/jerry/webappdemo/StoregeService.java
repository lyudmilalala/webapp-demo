package com.jerry.webappdemo;

public interface StoregeService {

    public void save(String prefix, String filename, byte[] content);

    public byte[] load(String prefix, String filename);
}
