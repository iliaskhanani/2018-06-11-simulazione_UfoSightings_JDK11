package it.polito.tdp.ufo.model;

public class Anno {
	
	private Integer anno;
	private Integer avvistamenti;
	
	public Anno(Integer anno, Integer avvistamenti) {
		super();
		this.anno = anno;
		this.avvistamenti = avvistamenti;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public Integer getAvvistamenti() {
		return avvistamenti;
	}

	public void setAvvistamenti(Integer avvistamenti) {
		this.avvistamenti = avvistamenti;
	}

	@Override
	public String toString() {
		return this.anno + " [" + avvistamenti + "]";
	}
	
	

}
