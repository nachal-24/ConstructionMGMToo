package mgmt.construction.constructionmgmt.Classes;

import android.util.Log;

/**
 * Created by Recluse on 1/18/2016.
 */

public class NonInstantEconomicOrderQuantity {
    double carryingCost;
    double costPerOrder;
    double totalQuantity;
    double totalDays;
    double optimumOrderSize;
    double productionRate;
    double demandRate;
    double maximumInventoryLevel;
    double productionRun;
    double totalInventoryCost;
    double noOfProductionRunsPerCycle;
    String startDate;
    String endDate;
    char type;
    String itemName;
    String workDetail;
    public NonInstantEconomicOrderQuantity(){};
    public NonInstantEconomicOrderQuantity(double carryingCost,double costPerOrder,double totalQuantity,
                                           double totalDays,double productionRate,String startDate,String endDate,String itemName,String workDetail)
    {
        this.carryingCost = carryingCost;
        this.costPerOrder = costPerOrder;
        this.totalDays = totalDays;
        this.totalQuantity = totalQuantity;
        this.productionRate = productionRate;
        this.startDate=startDate;
        this.endDate=endDate;
        this.itemName=itemName;
        this.type='N';
        this.workDetail=workDetail;
    }

    public String getWorkDetail() {
        return workDetail;
    }

    public void setWorkDetail(String workDetail) {
        this.workDetail = workDetail;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public void setProductionRate(double productionRate) {
        this.productionRate = productionRate;
    }

    public void setDemandRate(double demandRate) {
        this.demandRate = demandRate;
    }

    public void setMaximumInventoryLevel(double maximumInventoryLevel) {
        this.maximumInventoryLevel = maximumInventoryLevel;
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

    public double getProductionRate() {
        return productionRate;
    }

    public double getDemandRate() {
        return demandRate;
    }

    public double getMaximumInventoryLevel() {
        return maximumInventoryLevel;
    }

    public double getNoOfProductionRunsPerCycle() {
        return noOfProductionRunsPerCycle;
    }

    public void setOptimumOrderSize(double optimumOrderSize) {
        this.optimumOrderSize = optimumOrderSize;
    }

    public double getOptimumOrderSize() {
        return optimumOrderSize;
    }

    public double demandRate()
    {
        demandRate=(totalQuantity/totalDays);
        setDemandRate(demandRate);
        return  demandRate;

    }
    public double optimalOrderSize()
    {
/*
* The optimal order size is
* Qopt=sqrt((2*Co*D)/Cc*(1-(d/p)))
*/
        double tempOptOrderSize=0;
        double optimumOrderSize=0;
        demandRate();
        double tempDenomPart=1-(demandRate/productionRate);
        Log.v("SQUAREROOT",""+tempDenomPart);
        tempOptOrderSize = (2 * costPerOrder * totalQuantity) / (carryingCost*tempDenomPart);
        Log.v("SQUAREROOT",""+tempOptOrderSize);
        optimumOrderSize = Math.sqrt(tempOptOrderSize);
        Log.v("SQUAREROOT",""+optimumOrderSize);
        setOptimumOrderSize(optimumOrderSize);
        return optimumOrderSize;
    }
    public double totalAnnualMinimumInventoryCost(){
/*
* The total annual inventory cost
* TC=((Co*D)/Qopt)+((Cc*Qopt)/2)+(1-(d/p))
*/
        if(optimumOrderSize==0)
            return 0;
        else {
            totalInventoryCost = ((costPerOrder * totalQuantity) / optimumOrderSize) + ((carryingCost * optimumOrderSize) / 2)+(1-(demandRate/productionRate));
            return totalInventoryCost;
        }
    }

    public double noOfProductionRunsPerCycle()
    {
/*
* number of Production Runs per cycle
* D/Qopt
*/
        if(optimumOrderSize==0)
            return 0;
        else {
            noOfProductionRunsPerCycle = totalQuantity / optimumOrderSize;
            return noOfProductionRunsPerCycle;
        }
    }

    public double getProductionRun() {
        return productionRun;
    }

    public String getEndDate() {
        return endDate;
    }

    public char getType() {
        return type;
    }

    public double getTotalInventoryCost() {
        return totalInventoryCost;
    }

    public String getStartDate() {
        return startDate;
    }

    public double productionRun()
    {
/*
* Production Run
* Qopt/p
*/
        if(optimumOrderSize==0)
            return 0;
        else {
            productionRun =  optimumOrderSize/productionRate;
            return productionRun;
        }
    }

    public double maximumInventoryLevel()
    {
  /*
  * Maximum Inventory Level
  * Q(1-(d/p))
 */
        if(optimumOrderSize==0)
            return 0;
        else {
            maximumInventoryLevel =  optimumOrderSize*(1-(demandRate/productionRate));
            return maximumInventoryLevel;
        }
    }
}
