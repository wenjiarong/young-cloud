package org.springyoung.auth.support;

import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springyoung.auth.entity.YoungAuthUser;
import org.springyoung.core.constant.TokenConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt返回参数增强
 */
@AllArgsConstructor
public class YoungJwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        YoungAuthUser principal = (YoungAuthUser) authentication.getUserAuthentication().getPrincipal();

        //token参数增强
        Map<String, Object> info = new HashMap<>(16);
        info.put(TokenConstant.USER_ID, principal.getUserId());
        info.put(TokenConstant.ACCOUNT, principal.getAccount());
        info.put(TokenConstant.DEPT_ID, principal.getDeptId());
        info.put(TokenConstant.TENANT_ID, principal.getTenantId());
        info.put(TokenConstant.USER_NAME, principal.getUsername());
        info.put(TokenConstant.ROLE_IDS, principal.getRoleIds());
        info.put(TokenConstant.ROLE_NAMES, principal.getRoleNames());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

        return accessToken;
    }

}
