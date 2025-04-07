<%@ page import="org.apereo.cas.client.authentication.AttributePrincipal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cssGenerique">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</c:set>
<c:set var="jsGenerique">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</c:set>
<jsp:useBean id="themeUA" class="fr.univangers.beans.ThemeUA2">
    <jsp:setProperty name="themeUA" property="request" value="${pageContext.request}"/>
    <!-- Si le projet n'a pas de CAS ne pas renseigner les variables nom_individu et prenom_individu -> Supprimer les 4 lignes ci-dessous -->
    <c:if test="${not empty pageContext.request.remoteUser}">
        <jsp:setProperty name="themeUA" property="nom_individu" value='<%= ((AttributePrincipal)request.getUserPrincipal()).getAttributes().get("sn").toString()%>'/>
        <jsp:setProperty name="themeUA" property="prenom_individu" value='<%= ((AttributePrincipal)request.getUserPrincipal()).getAttributes().get("givenName").toString()%>'/>
    </c:if>
    <jsp:setProperty name="themeUA" property="css_generique" value="${cssGenerique}"/>
    <jsp:setProperty name="themeUA" property="css_specifique" value="${cssSpec}"/>
    <jsp:setProperty name="themeUA" property="js_generique" value="${jsGenerique}"/>
    <jsp:setProperty name="themeUA" property="js_specifique" value="${jsSpec}"/>
    <spring:message code='titre.application' var="titreApplication"/>
    <jsp:setProperty name="themeUA" property="titre_application" value="${titreApplication}"/>
    <jsp:setProperty name="themeUA" property="titre_page" value="${titrePage}"/>

    <%--Pour afficher le menu sur la gauche, à commenter si pas de menu--%>
    <%--<jsp:setProperty name="themeUA" property="affichage_menu" value="true"/>--%>

    <!-- Si vous avez fait une aide vous pouvez la renseigner ici : -->
    <%--<jsp:setProperty name="themeUA" property="aide" value="https://www.univ-angers.fr/intranet/fr/vie-institutionnelle/numerique/applications/apogee.html"/>--%>

    <!-- Le lien de contact doit être par défaut le helpdesk, si tout de fois vous devez renseigner une adresse email renseigner le ici : -->
    <%--<jsp:setProperty name="themeUA" property="email_contact" value="webappli@univ-angers.fr"/>--%>

    <%--Pour ne pas inclure le bandeau ou le footer--%>
    <%--<jsp:setProperty name="themeUA" property="affichage_bandeau" value="false"/>--%>
    <%--<jsp:setProperty name="themeUA" property="affichage_piedPage" value="false"/>--%>
</jsp:useBean>
${themeUA.header()}

<main class="flex-shrink-0">
    <div class="container-fluid p-0 ">
        <div id="content" class="w-100">
            <div class="bg-light-2  p-4">
                <h1>${titrePage}</h1>
            </div>
            <div class="container p-5">
                ${contenuSpec}
            </div>
        </div>
    </div>
</main>

${themeUA.footer()}
