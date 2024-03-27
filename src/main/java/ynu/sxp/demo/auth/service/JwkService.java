package ynu.sxp.demo.auth.service;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;
import ynu.sxp.demo.common.util.RsaKeyTool;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;


// 用于生成和管理 JWK（Json Web Key）
@Service
public class JwkService {
    // 用于保存非对称密钥文件的文件名
    private static final String RSA_KEY_FILE_NAME = "demo-app-rsa.key";

    private final KeyPair keyPair;

    public JwkService() throws Exception {
        this.keyPair = RsaKeyTool.getOrCreateKeyPair(RSA_KEY_FILE_NAME);
    }

    public RSAPublicKey getPublicKey() {
        return (RSAPublicKey) this.keyPair.getPublic();
    }

    public JWKSet jwkSet() {
        var rsaKey = new RSAKey.Builder(getPublicKey()).privateKey(this.keyPair.getPrivate()).build();
        return new JWKSet(rsaKey);
    }

    // JWT 编码器
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableJWKSet<SecurityContext>(jwkSet()));
    }

    // 生成 JWT 令牌字符串
    public String encodeJwt(JwtClaimsSet claims) {
        return this.jwtEncoder().encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}

