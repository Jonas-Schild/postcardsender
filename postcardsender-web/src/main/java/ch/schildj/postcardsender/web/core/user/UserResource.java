package ch.schildj.postcardsender.web.core.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class gets the logged user info of the application.
 */
@Api(value="user", description="Current user and environment")
@RestController
public class UserResource {


    @Value("${ch.schildj.environment}")
    private String env;

    @ApiOperation(value = "Get the current user")
    @RequestMapping(value = {"api/users/current"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserInfo userInfo() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        List<String> roles = auth.getAuthorities().stream().map(ga -> ga.getAuthority()).collect(Collectors.toList());

        return new UserInfo(name, roles);

    }

    @ApiOperation(value = "Get the current environment")
    @RequestMapping(value = {"api/environment"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String envInfo() {

        return env;
    }


}
