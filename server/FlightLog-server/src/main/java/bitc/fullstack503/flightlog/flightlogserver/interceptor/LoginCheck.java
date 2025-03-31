package bitc.fullstack503.flightlog.flightlogserver.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheck implements HandlerInterceptor {


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        세션 정보 가져오기.
    HttpSession session = request.getSession();

    System.out.println("\n--------Interceptor 동작---------\n");

//        세션 정보가 있는지 확인
    if (session.getAttribute("userId") == null) {
      System.out.println("로그아웃 상태");

      response.sendRedirect("/login/");

//        필터 통과 실패
      return false;
    } else {
//        세션 정보가 있을 경우
      System.out.println("로그인 상태, " + (String) session.getAttribute("userId"));

//        세션 사용 시간 설정 다시 해줌
      session.setMaxInactiveInterval(60 * 60 * 1);

//        필터 통과
      return true;
    }
  }
}