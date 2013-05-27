package treecalc.rt;


public final class MemoStatistic {
	   final private int id;
	   private int removed;
	   private int replaced;
	   private int hit;
	   private int miss;
	   private int killer;

	   public MemoStatistic(int id) {
		   this.id = id;		
	   }
	   @Override
	   public int hashCode() {
		   final int prime = 31;
		   int result = 1;
		   result = prime * result + id;
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
		   MemoStatistic other = (MemoStatistic) obj;
		   if (id != other.id)
			   return false;
		   return true;
	   }
	   public void incRemoved() {
		   removed++;
	   }
	   public void incReplaced() {
		   replaced++;
	   }
	   public void incHit() {
		   hit++;
	   }
	   public void incMiss() {
		   miss++;
	   }
	   public void incKiller() {
		   killer++;
	   }
	   public int getId() {
		   return id;
	   }
	   public int getRemoved() {
		   return removed;
	   }
	   public int getReplaced() {
		   return replaced;
	   }
	   public int getHit() {
		   return hit;
	   }
	   public int getMiss() {
		   return miss;
	   }
	   public int getKiller() {
		   return killer;
	   }
	@Override
	public String toString() {
		return "MemoStatistic [id=" + id + ", removed=" + removed
				+ ", replaced=" + replaced + ", hit=" + hit + ", miss=" + miss
				+ ", killer=" + killer + "]";
	}
}
