package ynu.sxp.demo.common.util;

import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.security.KeyPair;

@CommonsLog
public class RsaKeyTool {

    public static KeyPair getOrCreateKeyPair(String filename) {
        try {
            return loadKeyPairFromFile(filename);
        } catch (Exception e) {
            var keyPair = generateRSAKeyPair();
            try {
                saveKeyPairToFile(keyPair, filename);
            } catch (Exception ex) {
                log.error("保存密钥对到文件失败", ex);
            }
            return keyPair;

        }
    }
    // 生成 RSA 密钥对
    @SneakyThrows
    public static KeyPair generateRSAKeyPair() {
        java.security.KeyPairGenerator keyPairGenerator = java.security.KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    // 将密钥对保存到文件
    public static void saveKeyPairToFile(KeyPair keyPair, String filename) throws Exception {
        var fileStream = new FileOutputStream(filename);
        try (var objectOutputStream = new ObjectOutputStream(fileStream)) {
            objectOutputStream.writeObject(keyPair);
            log.info("已创建新的密钥对文件: " + Paths.get(filename).toAbsolutePath());
        }
    }

    // 从文件中加载密钥对
    public static KeyPair loadKeyPairFromFile(String filename) throws Exception {
        try (var objectInputStream = new ObjectInputStream(new FileInputStream(filename))) {
            return (KeyPair) objectInputStream.readObject();
        }
    }
}
