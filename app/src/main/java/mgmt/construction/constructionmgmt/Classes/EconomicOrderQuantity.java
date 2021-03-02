package mgmt.construction.constructionmgmt.Classes;

/**
 * Created by Recluse on 1/18/2016.
 */
public class EconomicOrderQuantity {
    double carryingCost;
    double costPerOrder;
    double totalQuantity;
    double totalDays;
    double optimumOrderSize;
    double totalInventoryCost;
    double noOfOrdersPerCycle;
    double ordesCycleTime;
    String startDate;
    String endDate;
    char type;
    String itemName;
String workDetail;
    public EconomicOrderQuantity(){}
    public EconomicOrderQuantity(double carryingCost,double costPerOrder,double totalQuantity,double totalDays
    ,String startDate,String endDate,String itemName,String workDetail)
    {
        this.carryingCost = carryingCost;
        this.costPerOrder = costPerOrder;
        this.totalDays = totalDays;
        this.totalQuantity = totalQuantity;
        this.startDate=startDate;
        this.endDate=endDate;
        this.itemName=itemName;
        this.type='E';
        this.workDetail=workDetail;
    }

    public String getWorkDetail() {
        return workDetail;
    }

    public void setWorkDetail(String workDetail) {
        this.workDetail = workDetail;
    }

    public char getType() {
        return type;
    }

    public void setCarryingCost(double carryingCost) {
        this.carryingCost = carryingCost;
    }

    public void setCostPerOrder(double costPerOrder) {
        this.costPerOrder = costPerOrder;
    }

    public void setTotalDays(double totalDays) {
        this.totalDays = totalDays;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getCarryingCost() {
        return carryingCost;
    }

    public double getCostPerOrder() {
        return costPerOrder;
    }

    public double getTotalDays() {
        return totalDays;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setOptimumOrderSize(double optimumOrderSize) {
        this.optimumOrderSize = optimumOrderSize;
    }

    public double getOptimumOrderSize() {
        return optimumOrderSize;
    }

    public double getNoOfOrdersPerCycle() {
        return noOfOrdersPerCycle;
    }

    public double getOrdesCycleTime() {
        return ordesCycleTime;
    }

    public double getTotalInventoryCost() {
        return totalInventoryCost;
    }

    public String getItemName() {
        return itemName;
    }

    public double optimalOrderSize()
    {
        /*
* The optimal order size is
* Qopt=sqrt((2*Co*D)/Cc)
*/
        double tempOptOrderSize=0;
        double optimumOrderSize=0;
        tempOptOrderSize = (2 * costPerOrder * totalQuantity) / carryingCost;
        optimumOrderSize = Math.sqrt(tempOptOrderSize);
        setOptimumOrderSize(optimumOrderSize);
        return optimumOrderSize;
    }
    public double totalAnnualMinimumInventoryCost(){
        /*
* The total annual inventory cost
* TC=((Co*D)/Qopt)+((Cc*Qopt)/2)
*/
        if(optimumOrderSize==0)
            return 0;
        else {
            totalInventoryCost = ((costPerOrder * totalQuantity) / optimumOrderSize) + ((carryingCost * optimumOrderSize) / 2);
            return totalInventoryCost;
        }
    }

    public double noOfOrdersCycle()
    {
        /*
* number of orders per cycle
* D/Qopt
*/
        if(optimumOrderSize==0)
            return 0;
        else {
            noOfOrdersPerCycle = totalQuantity / optimumOrderSize;
            return noOfOrdersPerCycle;
        }
    }
    public double ordersCycleTime()
    {
        /*
* Orders Cycle Time
* Total Days/(totalQuantity/Qopt)
*/
        if(optimumOrderSize==0)
            return 0;
        else {
            ordesCycleTime = totalDays / (totalQuantity / optimumOrderSize);
            return ordesCycleTime;
        }
    }
}
