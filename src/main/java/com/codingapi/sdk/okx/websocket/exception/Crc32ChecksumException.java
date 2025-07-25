package com.codingapi.sdk.okx.websocket.exception;

public class Crc32ChecksumException extends RuntimeException{

    public Crc32ChecksumException(int nowCrc, int checksum) {
        super(String.format("crc32 checksum error. nowChecksum:%s targetChecksum:%s",nowCrc,checksum));
    }
}
