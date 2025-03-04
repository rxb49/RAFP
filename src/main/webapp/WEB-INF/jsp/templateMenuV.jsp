<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--<nav class="bg-light veritcal-nav" id="sidebar">--%>
<%--    <ul class="nav flex-column pt-5 px-3">--%>
<%--        <li class="nav-item"><a class="nav-link px-0" href="${pageContext.request.contextPath}/"><i class="bi bi-people-fill pe-2"></i>Accueil</a>--%>
<%--        </li>--%>
<%--        <li class="nav-item"><a class="nav-link px-0 <c:if test="${currentUrl == '/personList'}">active</c:if>" href="${pageContext.request.contextPath}/personList"><i class="bi bi-people-fill pe-2"></i>Liste des devs Java</a>--%>
<%--        </li>--%>
<%--        <li class="nav-item"><a class="nav-link px-0 <c:if test="${currentUrl == '/hello'}">active</c:if>" href="${pageContext.request.contextPath}/hello"><i class="bi bi-balloon-fill pe-2"></i>Vous êtes qui ?</a>--%>
<%--        </li>--%>
<%--    </ul>--%>
<%--</nav>--%>

<nav class="bg-light veritcal-nav" id="sidebar">
    <ul class="nav flex-column pt-5 px-3">
        <c:forEach items="${leMenu}" var="m">
            <li class="nav-item"><a class="nav-link px-0 <c:if test="${m.actif}">active</c:if>" href="${pageContext.request.contextPath}${m.lien}"><i class="bi ${m.icone} pe-2"></i>${m.nom}</a></li>
        </c:forEach>
    </ul>
</nav>
<%-- https://icons.getbootstrap.com/ --%>