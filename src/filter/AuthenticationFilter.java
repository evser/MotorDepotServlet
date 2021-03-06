package filter;

import action.util.ActionEnum;
import bean.UserInfoBean;
import util.PagesBundleManager;
import util.PageNameConstant;
import util.RequestParameterName;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter used to check the page the client is requesting.
 * Created by USER on 15.05.2016.
 */
public class AuthenticationFilter implements Filter {

    private static final ActionEnum[] ADMIN_COMMANDS = { ActionEnum.CHANGE_TRUCK_STATE,
            ActionEnum.CHANGE_TRIP_STATE, ActionEnum.ADD_REQUEST, ActionEnum.GET_REQUESTS,
            ActionEnum.GET_TRIPS, ActionEnum.GET_ASSIGNATION_FORM, ActionEnum.GET_TRUCKS, ActionEnum.ASSIGN_DRIVER_TO_A_TRIP
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String contextPath = req.getContextPath();
        HttpSession session = req.getSession(false);
        String command = req.getParameter(RequestParameterName.COMMAND);
        ActionEnum actionEnum = ActionEnum.valueOf(command.toUpperCase());
        UserInfoBean userInfoBean = (UserInfoBean) session.getAttribute(RequestParameterName.USER);
        if(userInfoBean == null) {
            if(!ActionEnum.SIGNUP_FORM.equals(actionEnum) && !ActionEnum.SIGNUP.equals(actionEnum)
                    && !ActionEnum.LOGIN.equals(actionEnum) && !ActionEnum.GET_LOGIN_FORM.equals(actionEnum)) {
                res.sendRedirect(contextPath + PagesBundleManager.getProperty(PageNameConstant.LOGIN));
                return;
            }
            else {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        else if(!userInfoBean.isAdmin()) {
            for(ActionEnum forbiddenCommand : ADMIN_COMMANDS) {
                if(actionEnum == forbiddenCommand) {
                    res.sendRedirect(contextPath + PagesBundleManager.getProperty(PageNameConstant.LOGIN));
                    return;
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
