<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="titrePage"><spring:message code="error.navigateur"/></c:set>
<c:set var="cssSpec">
</c:set>
<c:set var="contenuSpec">
    <div class="container">
        <div class="row">
            <div class="col text-center my-5">
                <div>
                    <img src="/images/images/habilitation.png" class="img-fluid mb-3"
                         width="250px" />
                </div>
                <h1><spring:message code="error.navigateur" /></h1>
                <p><spring:message code="error.navigateur.recent" /></p>
                <div class="row">
                    <div class="col-sm mb-4">
                        <a href="https://www.mozilla.org/fr/firefox/new/"><img src="/images/images/firefox.png" class="img-fluid mb-2" width="70px">
                            <div>Firefox</div>
                        </a>
                    </div>
                    <div class="col-sm mb-4">
                        <a href="https://www.google.com/intl/fr_fr/chrome/"><img src="/images/images/chrome.png" class="img-fluid mb-2" width="70px">
                            <div>Chrome</div>
                        </a>
                    </div>
                    <div class="col-sm">
                        <a href="https://www.microsoft.com/fr-fr/edge"><img src="/images/images/edge.png" class="img-fluid mb-2" width="70px">
                            <div>Edge</div>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:set>
<c:set var="jsSpec">
</c:set>
<%@ include file="../templatePageSansMenuV.jsp"%>
