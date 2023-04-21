package covid.analysis.entity;

import java.time.LocalDate;

/**
 * @author Alok Kumar
 * 
 * This is the Entity Class of the Application.
 *
 */
public class CovidData {

	private int id;
	private LocalDate date;
	private String state;
	private String district;
	private int tested;
	private int confirmed;
	private int recovered;

	public CovidData() {
		super();
	}

	public CovidData(int id, LocalDate date, String state, String district, int tested, int confirmed, int recovered) {
		super();
		this.id = id;
		this.date = date;
		this.state = state;
		this.district = district;
		this.tested = tested;
		this.confirmed = confirmed;
		this.recovered = recovered;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public int getTested() {
		return tested;
	}

	public void setTested(int tested) {
		this.tested = tested;
	}

	public int getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}

	public int getRecovered() {
		return recovered;
	}

	public void setRecovered(int recovered) {
		this.recovered = recovered;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + confirmed;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((district == null) ? 0 : district.hashCode());
		result = prime * result + id;
		result = prime * result + recovered;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + tested;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CovidData other = (CovidData) obj;
		if (confirmed != other.confirmed)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
			return false;
		if (id != other.id)
			return false;
		if (recovered != other.recovered)
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (tested != other.tested)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CovidData [id=" + id + ", date=" + date + ", state=" + state + ", district=" + district + ", tested="
				+ tested + ", confirmed=" + confirmed + ", recovered=" + recovered + "]";
	}

	

}
