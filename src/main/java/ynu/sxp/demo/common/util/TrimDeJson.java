package ynu.sxp.demo.common.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

// 用于在反串行化JSON字符串时去除其两端的空格
public class TrimDeJson extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        var str = jsonParser.getText();
        try {
            return (str != null)? str.trim(): null;
        }catch (Exception e){
            throw new IOException("字符串去除空格失败");
        }
    }
}
