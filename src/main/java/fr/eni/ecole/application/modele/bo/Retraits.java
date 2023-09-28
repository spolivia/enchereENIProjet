package fr.eni.ecole.application.modele.bo;

public class Retraits {

	private int noArticle;
	private String rue;
	private int codePostale;
	private String ville;

	public Retraits() {

	}

	public Retraits(int noArticle, String rue, int codePostale, String ville) {
		this.noArticle = noArticle;
		this.rue = rue;
		this.codePostale = codePostale;
		this.ville = ville;
	}

	public int getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public int getCodePostale() {
		return codePostale;
	}

	public void setCodePostale(int codePostale) {
		this.codePostale = codePostale;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

}
