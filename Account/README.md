# Мессенджер
## На первом этапе требуется выполнить
- Написать JSP страницу /signUp на которой будет форма для регистрации. Данные для регистрации:
  -- Логин\Пароль
  -- ФИО
  -- Дата рождения
- Зарегистрировать пользователя, значит сохранить данные о нём в приложении
- Написать JSP страницу /signIn на которой будет форма для входа. (логин\пароль). Если не нашли пользователя или не подошел пароль, выдать ошибку.
- Написать JSP страницу /message где при GET запросе будет отображаться форма для отправки сообщения (логин кому\текст), при POST запросе отправляться сообщение.
- Написать JSP страницу /chats на которой будут отображаться сообщения отправленные текущему пользователю.
  -- Дата\время отправки
  -- От кого
  -- Текст
- Подключите SecurityFilter к проекту

```java
@WebFilter(urlPatterns = {"/chats", "/message"})
public class SecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String contextPath = req.getContextPath();
        HttpSession session = req.getSession(false);
        if((session!=null) && (session.getAttribute("user") != null)) {
            chain.doFilter(request, response);
        } else {
            //редирект на логин
            res.sendRedirect(contextPath + "/signIn");
        }
    }
}
```

org.springframework.web.servlet.DispatcherServlet