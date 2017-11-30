# Chemical Inventory Calculator

This app is the mobile side to the [Desktop version](https://github.com/thejbix/Inventory). This app is designed to keep track of a Chemical Inventory for commercial aerial spraying businesses.  A database is hosted online and the desktop java application interacts with the database to keep track of current inventory status and calculates future demand. 

## Login 
The app open to the login activity.  Here the app pulls the employees from the database and the user can select their name from the list.  When a user reports a chemical mix has been loaded it will update the database with who loaded that order.

![alt text][Login]

## Chemical Inventory Chart
The chart has two groups of data.  The first group is how much chemical is on hand while the second is how much chemical is needed to complete orders.
When a chemical mix has been loaded onto a spray plane to be carried out this program (or the accompanying mobile version of this program) can be used to report that the chemical has been loaded.  

![alt text][ChemChart]

## Pending Orders
Pending Orders are displayed in a table.  Pending orders may be loaded but not yet completed. Orders can be viewed in more depth by selecting the row.

![alt text][OrderTable]

## View Orders
Orders can be viewed in this app but not edited.  Neither employees or chemicals can be edited in this app.   A user can report that the order has been loaded. This action updates the Chemical Inventory by subtracting the chemical mix from the total inventory.

![alt text][ViewOrder]

## Authors

* **Jaydon Bixenman** 

[ChemChart]: https://github.com/thejbix/InventoryAndroid/raw/master/Pictures/chemChart.png "Chemical Chart"
[Login]: https://github.com/thejbix/InventoryAndroid/raw/master/Pictures/Login.png "Login"
[OrderTable]: https://github.com/thejbix/InventoryAndroid/raw/master/Pictures/OrderTable.png "Orders Pending"
[ViewOrder]: https://github.com/thejbix/InventoryAndroid/raw/master/Pictures/ViewOrder.png "View Order"

