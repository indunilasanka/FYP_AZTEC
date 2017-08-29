package aztec.rbir_rest2.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

//import aztec.rbir.rest2.security.TokenAuthentication;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.core.Authentication;
import java.util.*;


@RestController
@RequestMapping(value = "/user")
public class LoginController {
	
    @Autowired
    private TokenStore tokenStore;
    
    private ApprovalStore approvalStore;
    

    @Autowired
    private ConsumerTokenServices consumerTokenServices;
	
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization",
        value = "Bearer access_token",
        required = true,
        dataType = "string",
        paramType = "header"),
    })
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	protected String register(final HttpServletRequest req) {
	   return "works";
	}
    

    
    
    /*
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization",
        value = "Bearer access_token",
        required = true,
        dataType = "string",
        paramType = "header"),
    })
    @RequestMapping(value = "/oauth/revoke-token", method = RequestMethod.GET)
    //@ResponseStatus(HttpStatus.OK)
    public void removeAccessToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
            
            consumerTokenServices.revokeToken(tokenValue);
        }
    }

    @RequestMapping(value = "/oauth/revoke-refresh-token", method = RequestMethod.GET)
    //@ResponseStatus(HttpStatus.OK)
    public void removeRefreshToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2RefreshToken refreshToken = tokenStore.readRefreshToken(tokenValue);
            tokenStore.removeRefreshToken(refreshToken);

        }
    }
    */
}
