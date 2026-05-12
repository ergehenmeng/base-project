package com.eghm.web.controller;

import java.io.*;
import java.net.*;
import java.nio.file.*;

/**
 * Expose your local web server
 * loclx tunnel http --to localhost:3000
 * Custom subdomain
 * loclx tunnel http --to 3000 --subdomain awesome
 * (http, us) awesome.loclx.io => [running]
 * TCP tunnel for databases, SSH, etc.
 * loclx tunnel tcp --to localhost:22 --port 2222
 *
 * @author 殿小二
 * @since 2026/1/2
 */
public class FileTransferClient {
    private static final String SERVER_HOST = "192.168.1.100";
    private static final int SERVER_PORT = 8888;
    private static final String PROJECT_DIR = "D:\\IdeaProjects\\base-project";
    
    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            
            System.out.println("已连接到服务端: " + SERVER_HOST + ":" + SERVER_PORT);
            System.out.println("开始传输项目目录: " + PROJECT_DIR);
            
            Path projectPath = Paths.get(PROJECT_DIR);
            Files.walk(projectPath)
                    .filter(path -> !path.toFile().isHidden())
                    .forEach(path -> {
                        try {
                            String relativePath = projectPath.relativize(path).toString().replace("\\", "/");
                            
                            if (Files.isDirectory(path)) {
                                out.writeUTF("DIRECTORY");
                                out.writeUTF(relativePath);
                                String response = in.readUTF();
                                System.out.println("发送目录: " + relativePath + " - " + response);
                                
                            } else if (Files.isRegularFile(path)) {
                                long fileSize = Files.size(path);
                                
                                out.writeUTF("FILE");
                                out.writeUTF(relativePath);
                                out.writeLong(fileSize);
                                
                                try (FileInputStream fis = new FileInputStream(path.toFile())) {
                                    byte[] buffer = new byte[8192];
                                    int bytesRead;
                                    while ((bytesRead = fis.read(buffer)) != -1) {
                                        out.write(buffer, 0, bytesRead);
                                    }
                                }
                                
                                String response = in.readUTF();
                                System.out.println("发送文件: " + relativePath + " (大小: " + fileSize + " 字节) - " + response);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            
            out.writeUTF("END");
            String response = in.readUTF();
            System.out.println("传输完成: " + response);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}