<?php
include 'utils/DBHelper.php';

$EMail = $_POST['EMail'];

$DBHelper = new DBHelper();

$DBHelper ->selectTradeList($EMail);

?>