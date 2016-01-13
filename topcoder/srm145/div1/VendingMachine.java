import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

public class VendingMachine {
	
	public LinkedHashMap<Integer,Item> purchaseTimeToItemMap = new LinkedHashMap<Integer,Item>();
	
	public int motorUse(String[] prices, String[] purchases){
		int motorTime = 0;
		int doorPosition = 0;
		int expensivePosition = 0;
		int maxColumn = 0;
		
		try{
		
			List<Item> itemsList = this.createItemList(prices);
			this.setPurchaseTimes(purchases,itemsList);
			List<Column> columnsList = this.createColumnsList(itemsList);
			maxColumn = this.getMaximumColumnNumber(columnsList);
			
			List<Integer> purchaseTimeList = new ArrayList<Integer>(purchaseTimeToItemMap.keySet());
			expensivePosition = this.getExpensivePosition(columnsList);
			motorTime += this.rotateMotor(doorPosition, expensivePosition, maxColumn);
			doorPosition = expensivePosition;
			for(int i=0;i<purchaseTimeList.size();i++){
				
				int purchaseTime = purchaseTimeList.get(i);
				Item item = purchaseTimeToItemMap.get(purchaseTime);
				
				motorTime += this.rotateMotor(doorPosition, item.column, maxColumn);
				doorPosition = item.column;
				Column col = this.getColumnFromColumnList(item.column, columnsList);
				col.purchaseItem(item);
				
				int nextPurchaseTime = i+1!=purchaseTimeList.size()?purchaseTimeList.get(i+1):-1;
				if(nextPurchaseTime==-1 || nextPurchaseTime-purchaseTime>=5){
					expensivePosition = this.getExpensivePosition(columnsList);
					motorTime += this.rotateMotor(doorPosition, expensivePosition, maxColumn);
					doorPosition = expensivePosition;
				}
				
			}
		}catch(Exception e){
			motorTime = -1;
		}
		return motorTime;
	}
	
	public class Column{
		int columnNumber =0;
		List<Item> items =null;
		int columnCost =0;
		
		public Column(int columnNumber){
			this.columnNumber = columnNumber;
		}
		
		public void addItem(Item item){
			if(items==null)
				items = new ArrayList<Item>();
			items.add(item);
			columnCost += item.price;
		}
		
		public void purchaseItem(Item item){
			columnCost -= item.price;
		}
	}
	
	public class Item{
		String id; // shelf+"*"+column
		int shelf;
		int column;
		int price;
		int purchaseTime;
		
		public Item(int shelf, int column){
			this.shelf = shelf;
			this.column = column;
			this.id = shelf+"*"+column;
		}
		
		public void setPrice(int price){
			this.price = price;
		}
		
		public void setPurchaseTime(int time){
			this.purchaseTime = time;
		}
		
		public Item getItem(String id){
			if(this.id.equals(id))
				return this;
			else return null;
		}
	}
	
	private void setPurchaseTimes(String[] purchases, List<Item> itemsList) throws Exception{
		for(int i=0;i<purchases.length;i++){
			String[] purchaseDetails = purchases[i].split(",|:");
			Item item = this.getItemFromItemList(purchaseDetails[0],purchaseDetails[1], itemsList);
			if(item.purchaseTime!=0) throw new Exception();
			item.purchaseTime = Integer.parseInt(purchaseDetails[2]);
			purchaseTimeToItemMap.put(Integer.parseInt(purchaseDetails[2]),item);
		}
	}
	
	private List<Item> createItemList(String[] prices){
		List<Item> itemList = new ArrayList<Item>();
		for(int shelf=0;shelf<prices.length;shelf++){
			String[] columnPrices = prices[shelf].split(" ");
			for(int column=0;column<columnPrices.length;column++){
				Item item = new Item(shelf,column);
				item.setPrice(Integer.parseInt(columnPrices[column]));
				itemList.add(item);
			}
		}
		return itemList;
	}
	
	private Item getItemFromItemList(String shelf, String column, List<Item> itemsList){
		for(Item item: itemsList){
			if(item.id.equals(shelf+"*"+column))
				return item;
		}
		return null;
	}
	
	private List<Column> createColumnsList(List<Item> itemsList){
		List<Column> columnsList = new ArrayList<Column>();
		for(Item item: itemsList){
			Column col = this.getColumnFromColumnList(item.column, columnsList);
			if(col==null) col = new Column(item.column);
			col.addItem(item);
			columnsList.add(col);
		}
		return columnsList;
	}
	
	private Column getColumnFromColumnList(int column, List<Column> columnsList){
		for(Column col: columnsList){
			if(col.columnNumber==column)
				return col;
		}
		return null;
	}
	
	private int getExpensivePosition(List<Column> columnsList){
		int expensivePosition = 0;
		Collections.sort(columnsList, new Comparator<Column>(){
			public int compare(Column c1, Column c2){
				if(c1.columnCost==c2.columnCost)
					return c1.columnNumber - c2.columnNumber;
				return c2.columnCost - c1.columnCost;
			}
		});
		if(columnsList.size()>0) return columnsList.get(0).columnNumber;
		return expensivePosition;
	}
	
	private int rotateMotor(int fromPosition, int toPosition, int maxColumn){
		int rotateTime = 0;
		rotateTime = Math.abs(fromPosition - toPosition);
		rotateTime = Math.min(rotateTime, maxColumn +1 -Math.max(fromPosition, toPosition) + Math.min(fromPosition, toPosition));
		return rotateTime;
	}
	
	private int getMaximumColumnNumber(List<Column> columnsList){
		int maxColumn = -1;
		for(Column column: columnsList){
			if(column.columnNumber > maxColumn)
				maxColumn = column.columnNumber;
		}
		return maxColumn;
	}
	
}