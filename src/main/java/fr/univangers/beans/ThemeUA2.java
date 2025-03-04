package fr.univangers.beans;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

public class ThemeUA2 extends HttpServlet {

	private String login ;
	private String nom_individu;
	private String prenom_individu;
	private HttpServletRequest request;
	
	private String contact_sujet ;
	private String contact_commentaire ;

	private boolean affichage_bienvenue_deconnexion;
	private boolean affichage_langue;
	private boolean affichage_contact ;
	private String lien_logo;
	private boolean affichage_bandeau;
	private boolean affichage_piedPage;
	private String v_texte_aide_footer;
	private String v_texte_contact_footer;

	//Variable a definir par l'application
	private String titre_application ;
	private String titre_page ;
	private String aide ;
	private String email_contact;
	private String css_generique ;
	private String css_specifique ;
	private String js_generique ;
	private String js_specifique ;

	private boolean affichage_menu;

	private static final long serialVersionUID = 1L;

	public ThemeUA2() {
		super();

		//init
		this.css_generique = "";
		this.css_specifique = "";
		this.js_generique = "";
		this.js_specifique = "";
		this.titre_application = "";
		this.titre_page = "";
		this.aide = "";
		this.email_contact = "";
		this.contact_sujet = "";
		this.contact_commentaire = "";

		//Lien par default pour le logo
		this.lien_logo = "https://www.univ-angers.fr/";

		//Par default on affiche aide contact
		this.affichage_contact = true;
		this.affichage_bienvenue_deconnexion = true;
		this.affichage_langue = false;
		this.affichage_bandeau = true;
		this.affichage_piedPage = true;

		this.affichage_menu = false;
	}

	/**
	 * Affiche le bandeau sans menu avec le cas et la connexion a la base de donnees
	 * @return un string qui contient le BANDEAU avec une authentification CAS
	 */
	public String header() throws Exception{

		String v_texte_bienvenue = "";
		String v_texte_aide = "";
		v_texte_aide_footer = "";
		String v_texte_contact = "<a target='_blank' href='https://helpdesk.univ-angers.fr'><i class='fa fa-envelope mr-2'></i> Contact</a>\n";
		String v_texte_sous_titre = "<br />";
		String v_retour = "";
		String v_initiale = "";

		//Recuperation du LOGIN de la personne Connectee
		this.login = null;
		if (request.getRemoteUser() != null){
			this.login = request.getRemoteUser();
		}

		//TITRE : Si aucun titre on en affiche un par défault
		if (titre_application.equals("")){
			titre_application = "Application";
		}

		//Le titre de la page peut convenir : <i class=\"bi bi-people-fill pe-2\"></i>Liste des devs Java Application Modèle ; il faut supprimer la balise i
		if (titre_page != null){
			titre_page = titre_page.replaceAll("<i[^>]*></i>", "");
		}

		//AIDE
		if(!this.aide.equals("")) {
			v_texte_aide = "<a target='_blank' href='"+this.aide+"' class='g-mr-30'><i class='fa fa-question-circle mr-2'></i> Aide</a>\r\n";
			v_texte_aide_footer = "<li class='list-inline-item'><a target='_blank' href='"+this.aide+"'>Aide</a></li>";
		}
		
		//CONTACT
		if(this.email_contact != null && !this.email_contact.equals("")) {
			String nom_war_referer ;
			String nom_jsp_referer ;
			//Nom du WAR referer
			nom_war_referer = request.getContextPath().substring(1);

			//Nom du JSP referer
			nom_jsp_referer = "index.jsp"; //Valeur par default
			if (request.getRequestURI().length() > nom_war_referer.length()+2){
				nom_jsp_referer = request.getRequestURI().substring(nom_war_referer.length()+2);

				try{
					int taille = nom_jsp_referer.split(";").length;
					if (taille >= 1){

						if (nom_jsp_referer.split(";")[0].equals("")){
							nom_jsp_referer = "index.jsp"; //Valeur par default
						}
						else{
							nom_jsp_referer = nom_jsp_referer.split(";")[0];
						}
					}
				}
				catch(Exception e){}
			}

			v_texte_contact = " <a href='Contact' onclick=\"window.open('https://outils.univ-angers.fr/outils/Email.jsp?page="+nom_war_referer+"/"+nom_jsp_referer+"&sujet="+contact_sujet+"&email="+this.email_contact +"&commentaire="+contact_commentaire+"','popupMail','toolbar=0,location=0,directories=0,status=0,scrollbars=0,resizable=1,copyhistory=0,menuBar=0,width=600,height=400');return(false)\"><i class='fa fa-envelope mr-2'></i> Contact</a>\n";
			v_texte_contact_footer = "<li class='list-inline-item'><a href='Contact' onclick=\"window.open('https://outils.univ-angers.fr/outils/Email.jsp?page="+nom_war_referer+"/"+nom_jsp_referer+"&sujet="+contact_sujet+"&email="+this.email_contact +"&commentaire="+contact_commentaire+"','popupMail','toolbar=0,location=0,directories=0,status=0,scrollbars=0,resizable=1,copyhistory=0,menuBar=0,width=600,height=400');return(false)\">Contact</a></li>";
		}
		//Si on ne souhaite pas afficher le lien contact, on le supprime
		if (!affichage_contact) {
			v_texte_contact = "";
			v_texte_contact_footer = "";
			
		}
		
		//Affiche du texte Bienvenue et Deconnexion
		if (affichage_bienvenue_deconnexion && login != null){

			//Affichage des initiales de la personne connectée
			if (this.prenom_individu != null){
				v_initiale = String.valueOf(this.prenom_individu.charAt(0));
			}
			if (this.nom_individu != null){
				v_initiale += this.nom_individu.charAt(0);
			}

			v_texte_bienvenue =
					"          <button class=\"btn dropdown-btn d-flex align-items-center\" type=\"button\" data-bs-toggle=\"dropdown\"\n" +
					"            aria-expanded=\"false\">\n" +
					"            <span class=\"avatar\">"+v_initiale+"</span>\n" +
					"            <span class=\"dropdown-arrow\">></span>\n" +
					"          </button>\n" +
					"          <ul class=\"dropdown-menu\">\n" +
					"            <li><a class=\"dropdown-item\" href=\"#\">"+this.prenom_individu+" "+this.nom_individu+"</a></li>\n" +
					"            <li><a class=\"dropdown-item\" href=\"https://monprofil.univ-angers.fr/monprofil/moncompte\">Mon compte</a></li>\n" +
					"            <li><a class=\"dropdown-item\" href=\"https://casv6.univ-angers.fr/cas/logout?service="+lien_logo+"\">Déconnexion</a></li>\n" +
					"          </ul>\n" ;

			//Affichage du changement de la langue
			if (affichage_langue) {
				v_texte_bienvenue += "<a class=\"g-ml-40\" href=\"?lang=fr\">FR</a> | <a href=\"?lang=en\">EN</a> \r\n";
			}
		}
		else{
			//Affichage du changement de la langue
			if (affichage_langue) {
				v_texte_bienvenue = "<a href=\"?lang=fr\">FR</a> | <a href=\"?lang=en\">EN</a> \r\n";
			}
			else {
				v_texte_bienvenue = "<br /> \r\n";	
			}
			
		}


		v_retour = "<!DOCTYPE html> \r\n" +
//				"<html xmlns='http://www.w3.org/1999/xhtml' xml:lang='fr' lang='fr'> \r\n"+
				"<html lang='fr' > \r\n"+
				"<head> \r\n"+ 
				"   <title>"+titre_page+" - "+titre_application+"</title>\r\n"+
				//"	<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />\r\n"+
				"	<link rel='shortcut icon' href='/images/images/favicon.ico' type='image/x-icon' />\r\n"+
				" 	<meta charset='utf-8' />\r\n"+
//				"	<meta content='Universit&eacute; Angers' name='author' /> \r\n"+
//			    "	<meta content='Universit&eacute; Angers' name='copyright' /> \r\n"+
			    //"	<meta content='width=device-width, initial-scale=1, shrink-to-fit=no' name='viewport' />\r\n"+
			    //"	<!-- Google Fonts --> \r\n"+
			    //"	<link href='https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800' rel='stylesheet'>\r\n"+
			    "	<!-- CSS Global Compulsory -->\r\n"+
			    "	<link rel='stylesheet' href='"+request.getContextPath()+"/themeMQ/css/custom.css'> \r\n"+
			    this.css_generique+
			    this.css_specifique;
				
		v_retour +=
				"  	</head> \r\n"+
				" <body data-bs-theme=\"light\"> \r\n";
		
		if (affichage_bandeau){
			v_retour += 
				"<header class=\"bd-header\"> \r\n"+
			    "	<div class=\"gradient-line\"> \r\n"+
			    "   	<div class=\"container-fluid p-2 border-bottom shadow-sm\"> \r\n"+
			    "       	<div class=\"d-flex justify-content-between px-md-4\"> \r\n"+
				" 				<button class=\"btn btn-primary d-md-none\" id=\"sidebarCollapse\">\n" +
				" 					<span class=\"navbar-toggler-icon\"></span>\n" +
				" 				</button>\n"+
				"				<span class=\"ps-3 ps-md-0\">\n" +
				"   				<a id='lien_logo_ua' href='"+lien_logo+"'> \r\n"+
				"					   	<img src=\"/images/library/moco/img/ua_h_couleur.svg\" alt=\"\" height=\"40\">\n" +
				"   				</a> \r\n"+
				"				</span>\n"+
				"				<div class=\"my-auto mx-auto titre-page d-none d-md-block \">"+titre_application+"</div>\n"+
				"				<div class=\"dropdown\">\n" +
									// Menu Burger
									v_texte_bienvenue +
				"       		</div>\n" +
			"       		</div>\n" +
				"   	</div>\n" +
				"	</div>\n" +
				"</header>\n" ;
		}
		
		v_retour += "<noscript>"+
				"<div class='row'> "+
			    "    <div class='col text-center g-mb-40'> "+ 
			    "        <img src='/images/images/habilitation.png' class='img-fluid mb-3' width='250px' /> "+
			    "        <p>Pour votre s&eacute;curit&eacute; et votre confort, vous devez activer le javascript pour continuer.</p> "+
			    "    </div> "+
			    "</div> "+
			    "<style type='text/css'> "+
		        "	main { display:none; } "+
		        "</style> "+
			    "</noscript>";

		return v_retour;
	}

	/**
	 * Contient le bandeau Sans Menu / Sous Menu, sans BDD et avec le CAS
	 * @return le header sous forme de string
	 */
	public String headerSansBandeau() throws Exception{
		affichage_contact = true; 		// On affiche le lien Contact
		affichage_bandeau = false; 		// On n'affiche page le bandeau
		
		return header();
	}

	public String footer(){
		String v_retour = "";
		if (affichage_piedPage) {
			v_retour =
				"<footer class=\"footer  bg-primary\">\n" +
				"    <div class=\"container text-center\">\n" +
				"      <span class=\"text-white\">\n" +
				"        This is the sticky footer.\n" +
				"      </span>\n" +
				"    </div>\n" +
				"  </footer>";
		}

		v_retour += "  <script src=\"https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js\"></script>\n" +
			"  <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js\"\n" +
			"    integrity=\"sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4\"\n" +
			"    crossorigin=\"anonymous\"></script>\n" +
			// Inclusion JS
			this.js_generique+
			this.js_specifique;

		//Si on n'affiche pas le menu alors on ajoute le script pour le menu
		if (affichage_menu) {
			v_retour +="<div id=\"overlay\"></div>\n" +
				"  <script>\n" +
				"    document.addEventListener('DOMContentLoaded', function () {\n" +
				"      const sidebarCollapse = document.getElementById('sidebarCollapse');\n" +
				"      const sidebar = document.getElementById('sidebar');\n" +
				"      const overlay = document.getElementById('overlay');\n" +
				"      sidebarCollapse.addEventListener('click', function () {\n" +
				"        sidebar.classList.toggle('active');\n" +
				"        sidebarCollapse.classList.toggle('active');\n" +
				"        overlay.classList.toggle('active');\n" +
				"      });\n" +
				"    });\n" +
				"  </script>\n";
		}
		v_retour += "</body>\n" +
				"</html>";

		return v_retour;
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setTitre_application(String titre_application) {
		this.titre_application = titre_application;
	}

	public void setAffichage_contact(boolean affichage_contact) {
		this.affichage_contact = affichage_contact;
	}

	public void setCss_generique(String css_generique) {
		this.css_generique = css_generique;
	}

	public void setCss_specifique(String css_specifique) {
		this.css_specifique = css_specifique;
	}

	public void setJs_generique(String js_generique) {
		this.js_generique = js_generique;
	}

	public void setJs_specifique(String js_specifique) {
		this.js_specifique = js_specifique;
	}

	public void setAffichage_bienvenue_deconnexion(boolean affichage_bienvenue_deconnexion) {
		this.affichage_bienvenue_deconnexion = affichage_bienvenue_deconnexion;
	}

	public void setContact_sujet(String contact_sujet) {
		this.contact_sujet = contact_sujet;
	}

	public void setContact_commentaire(String contact_commentaire) {
		this.contact_commentaire = contact_commentaire;
	}

	public void setLien_logo(String lien_logo) {
		this.lien_logo = lien_logo;
	}

	public void setAffichage_bandeau(boolean affichage_bandeau) {
		this.affichage_bandeau = affichage_bandeau;
	}

	public void setAffichage_piedPage(boolean affichage_piedPage) {
		this.affichage_piedPage = affichage_piedPage;
	}

	public void setAffichage_langue(boolean affichage_langue) {
		this.affichage_langue = affichage_langue;
	}

	public void setAide(String aide) {
		this.aide = aide;
	}

	public void setEmail_contact(String email_contact) {
		this.email_contact = email_contact;
	}

	public void setNom_individu(String nom_individu) {
		this.nom_individu = nom_individu;
	}

	public void setPrenom_individu(String prenom_individu) {
		this.prenom_individu = prenom_individu;
	}

	public void setAffichage_menu(boolean affichage_menu) {
		this.affichage_menu = affichage_menu;
	}

	public String getTitre_page() {
		return titre_page;
	}

	public void setTitre_page(String titre_page) {
		this.titre_page = titre_page;
	}
}
