package com.eghm.interfaces.manage.controller;


import java.io.*;
import java.net.*;
import java.nio.file.*;

/**
 * @author 殿小二
 * @since 2026/1/2
 */
public class FileTransferServer {
    private static final int PORT = 8888;
    private static final String BASE_DIR = "D:\\IdeaProjects\\base-project-server";
    
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("服务端已启动，监听端口: " + PORT);
            System.out.println("接收目录: " + BASE_DIR);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("客户端已连接: " + clientSocket.getInetAddress());
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void handleClient(Socket clientSocket) {
        try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {
            
            while (true) {
                String command = in.readUTF();
                
                if ("DIRECTORY".equals(command)) {
                    String dirPath = in.readUTF();
                    Path targetDir = Paths.get(BASE_DIR, dirPath);
                    Files.createDirectories(targetDir);
                    System.out.println("创建目录: " + targetDir);
                    out.writeUTF("OK");
                    
                } else if ("FILE".equals(command)) {
                    String filePath = in.readUTF();
                    long fileSize = in.readLong();
                    Path targetFile = Paths.get(BASE_DIR, filePath);
                    
                    System.out.println("接收文件: " + targetFile + " (大小: " + fileSize + " 字节)");
                    
                    Files.createDirectories(targetFile.getParent());
                    
                    try (FileOutputStream fos = new FileOutputStream(targetFile.toFile())) {
                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        long totalRead = 0;
                        
                        while (totalRead < fileSize && (bytesRead = in.read(buffer, 0, (int) Math.min(buffer.length, fileSize - totalRead))) != -1) {
                            fos.write(buffer, 0, bytesRead);
                            totalRead += bytesRead;
                        }
                    }
                    
                    out.writeUTF("OK");
                    
                } else if ("END".equals(command)) {
                    System.out.println("传输完成");
                    out.writeUTF("OK");
                    break;
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
