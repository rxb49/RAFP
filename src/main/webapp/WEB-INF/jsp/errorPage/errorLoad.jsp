<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="titrePage"><spring:message code="error.errorLoad"/></c:set>
<c:set var="cssSpec">
</c:set>
<c:set var="contenuSpec">
    <div class="container" >
        <div class="row">
            <div class="col text-center g-mb-40">
                <div class="g-my-50">
                    <i class="fa fa-gear txtBlueF rotating" style="font-size: 4.5rem"></i>
                    <i class="fa fa-gear txtBlue rotating" style="font-size: 1.5rem"></i>
                    <i class="fa fa-gear txtBlueF rotating" style="font-size: 3.5rem"></i>
                </div>
                <p><b><spring:message code="error.errorLoad"/></b></p>
                <p><spring:message code="error.errorLoad2"/></p>
                <a href="${pageContext.request.contextPath}/" class="btn btn-md u-btn-outline-bluegray g-mt-20"><spring:message code="error.retour"/></a>
            </div>
        </div>
    </div>
</c:set>
<c:set var="jsSpec">
</c:set>
<%@ include file="../templatePageSansMenuV.jsp"%>