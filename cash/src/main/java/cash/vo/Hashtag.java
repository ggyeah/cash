package cash.vo;

public class Hashtag {
	private int cashbookNo;
	private String word;
	private String updatedate;
	private String cteatedate;
	public Hashtag() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Hashtag(int cashbookNo, String word, String updatedate, String cteatedate) {
		super();
		this.cashbookNo = cashbookNo;
		this.word = word;
		this.updatedate = updatedate;
		this.cteatedate = cteatedate;
	}
	public int getCashbookNo() {
		return cashbookNo;
	}
	public void setCashbookNo(int cashbookNo) {
		this.cashbookNo = cashbookNo;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	public String getCteatedate() {
		return cteatedate;
	}
	public void setCteatedate(String cteatedate) {
		this.cteatedate = cteatedate;
	}
	@Override
	public String toString() {
		return "Hashtag [cashbookNo=" + cashbookNo + ", word=" + word + ", updatedate=" + updatedate + ", cteatedate="
				+ cteatedate + "]";
	}
}
