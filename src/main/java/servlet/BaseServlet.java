package servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        try {
            Class clazz = this.getClass();
            String methodName = req.getParameter("method");
            if (methodName != null) {
                Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
                HttpServletRequest httpRequest = (HttpServletRequest) req;
                HttpServletResponse httpResponse = (HttpServletResponse) res;
                method.invoke(this, httpRequest, httpResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
