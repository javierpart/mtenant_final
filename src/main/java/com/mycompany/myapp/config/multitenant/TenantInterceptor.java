package com.mycompany.myapp.config.multitenant;

import static com.mycompany.myapp.config.multitenant.TenantConstants.AUTHORIZATION;
import static com.mycompany.myapp.config.multitenant.TenantConstants.ZERO;

import com.mycompany.myapp.security.jwt.JWTFilter;
import com.mycompany.myapp.security.jwt.TokenProvider;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

@Component
public class TenantInterceptor implements WebRequestInterceptor {

    private final String defaultTenant;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    public TenantInterceptor(@Value("${multitenancy.tenant.default-tenant:#{null}}") String defaultTenant) {
        this.defaultTenant = defaultTenant;
    }

    @Override
    public void preHandle(WebRequest request) {
        String token;
        String tenantId = ZERO;
        if (request.getHeader(AUTHORIZATION) != null) {
            token = request.getHeader(AUTHORIZATION).substring(7);
            System.out.println(token);
            if (tokenProvider.validateToken(token)) {
                System.out.println("se ha validado");
                String[] chunks = token.split("\\.");
                System.out.println(chunks.toString());
                Base64.Decoder decoder = Base64.getUrlDecoder();
                String payload = new String(decoder.decode(chunks[1]));
                System.out.println(payload);
                tenantId = stringToTenant(payload);
                System.out.println(tenantId);
            }
        }
        TenantContext.setTenantId(tenantId);
    }

    @Override
    public void postHandle(@NonNull WebRequest request, ModelMap model) {
        TenantContext.clear();
    }

    @Override
    public void afterCompletion(@NonNull WebRequest request, Exception ex) {
        // NOOP
    }

    //     public HashMap<String,String> stringToHashmap(String text) {

    //         //new HashMap object
    //         HashMap<String, String> hMapData = new HashMap<String, String>();

    //         //split the String by a comma
    //         String parts[] = text.split(",");
    //         System.out.println(parts.toString());

    //         //iterate the parts and add them to a map
    //         for(String part : parts){
    //             if(part.contains(":")){
    //                 //split the employee data by : to get id and name
    //                 if(part.contains("X-TENANT-ID")){
    //                     String empdata[] = part.split(":");

    //                     String strId = empdata[0].trim();
    //                     String strName = empdata[1].trim();

    //                     //add to map
    //                     hMapData.put(strId, strName);
    //                 }
    //             }
    //         }
    //         return hMapData;
    //     }
    // }
    public String stringToTenant(String text) {
        //new HashMap object
        HashMap<String, String> hMapData = new HashMap<String, String>();

        //split the String by a comma
        String parts[] = text.split(",");
        System.out.println(parts.toString());

        //iterate the parts and add them to a map
        for (String part : parts) {
            if (part.contains(":")) {
                //split the employee data by : to get id and name
                if (part.contains("X-TENANT-ID")) {
                    String empdata[] = part.split(":");

                    String strName = empdata[1].trim();
                    return strName;
                }
            }
        }
        return "0";
    }
}
