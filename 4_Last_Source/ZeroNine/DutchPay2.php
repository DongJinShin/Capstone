<?php
include 'utils/DBHelper.php';

$managerID = $_POST['managerID'];
$ID1 = $_POST['ID1'];
$ID2 = $_POST['ID2'];
$amount1 = $_POST['amount1'];
$amount2 = $_POST['amount2'];

$DBHelper = new DBHelper();

$DBHelper ->dutchPay2($managerID, $ID1, $ID2, $amount1, $amount2);
?>