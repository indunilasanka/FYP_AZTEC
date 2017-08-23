package aztec.rbir_rest2.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

//import aztec.rbir.rest2.security.TokenAuthentication;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import org.springframework.security.access.prepost.PreAuthorize;



@RestController
@RequestMapping(value = "/user")
public class LoginController {
	
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
    

    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization",
        value = "Bearer access_token",
        required = true,
        dataType = "string",
        paramType = "header"),
    })
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('NORMAL_USER')")
	protected String login(final HttpServletRequest req) {
	   return "works";
	}
	
}
