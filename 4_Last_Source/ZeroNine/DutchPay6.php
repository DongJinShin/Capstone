<?php
include 'utils/DBHelper.php';

$managerID = $_POST['managerID'];
$ID1 = $_POST['ID1'];
$ID2 = $_POST['ID2'];
$ID3 = $_POST['ID3'];
$ID4 = $_POST['ID4'];
$ID5 = $_POST['ID5'];
$ID6 = $_POST['ID6'];
$amount1 = $_POST['amount1'];
$amount2 = $_POST['amount2'];
$amount3 = $_POST['amount3'];
$amount4 = $_POST['amount4'];
$amount5 = $_POST['amount5'];
$amount6 = $_POST['amount6'];

$DBHelper = new DBHelper();

$DBHelper ->dutchPay6($managerID, $ID1, $ID2, $ID3, $ID4, $ID5, $ID6, $amount1, $amount2, $amount3, $amount4, $amounmt5, $amount6);
?>