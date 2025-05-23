package bitc.fullstack503.flightlog.flightlogserver.config;

import bitc.fullstack503.flightlog.flightlogserver.interceptor.LoginCheck;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

  // 사용자가 생성한 인터셉터 클래스를 추가
  @Override
  public void addInterceptors(InterceptorRegistry registry){
    registry.addInterceptor(new LoginCheck())
        .addPathPatterns("/main/*")
        // 메인 인터셉터 동작
        .excludePathPatterns("/main/")
        .excludePathPatterns("/main/storeDetail")
        .excludePathPatterns("/login/")
        .excludePathPatterns("/login/loginProcess");

  }


}