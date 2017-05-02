<?php
include 'utils/DBHelper.php';

$EMail = $_POST['EMail'];
$amount = $_POST['amount'];

$DBHelper = new DBHelper();

$DBHelper ->useCoin($EMail, $amount);
?>