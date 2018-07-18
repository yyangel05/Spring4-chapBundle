<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.kh.yytest.event.EventType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>이벤트 생성</title>
</head>
<body>

<form:form commandName="eventForm" action="/yytest/newevent/step3">

<label>적용 회원 등급</label>
<label><input type="radio" name="target" value="all" ${eventForm.target == 'all' ? 'checked' : '' }/>모든 회원</label>
<label><input type="radio" name="target" value="premium" ${eventForm.target == 'premium' ? 'checked' : '' }/>프리미엄 회원</label>
<form:errors path="target"/><br/>
<br/>
<a href="/yytest/newevent/step1">[이전단계로]</a>
<input type="submit" value="다음 단계로" />

</form:form>

세션 존재 여부 : <%= session.getAttribute("eventForm") != null ? "존재" : "없음" %>

</body>
</html>