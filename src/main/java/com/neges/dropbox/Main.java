/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neges.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;

public class Main {
    
    //neges
    private static final String ACCESS_TOKEN = "xIVbTWVv2E4AAAAAAABIOYim12SKI6oZXUdeyeRdtiSYhb6RFkv5WqG8J5vdwJo5";
    
    public static void main(String args[]) throws DbxException, FileNotFoundException, IOException {
        
        // Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        
        // Get current account info
        FullAccount account = client.users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());
        
        // Get files and folder metadata from Dropbox root directory
        ListFolderResult result = client.files().listFolder("/neges");
        while (true) {
            for (Metadata metadata : result.getEntries()) {
                System.out.println("Archivo: " + metadata.getPathLower());
            }

            if (!result.getHasMore()) {
                break;
            }

            result = client.files().listFolderContinue(result.getCursor());
            
        }
        
        // Upload "test.txt" to Dropbox
        try (InputStream in = new FileInputStream("D://demo/log.txt")) {
            FileMetadata metadata = client.files().uploadBuilder("/test.txt")
                .uploadAndFinish(in);
        }
    }
}