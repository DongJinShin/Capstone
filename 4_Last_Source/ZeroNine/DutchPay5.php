<?php
include 'utils/DBHelper.php';

$managerID = $_POST['managerID'];
$ID1 = $_POST['ID1'];
$ID2 = $_POST['ID2'];
$ID3 = $_POST['ID3'];
$ID4 = $_POST['ID4'];
$ID5 = $_POST['ID5'];
$amount1 = $_POST['amount1'];
$amount2 = $_POST['amount2'];
$amount3 = $_POST['amount3'];
$amount4 = $_POST['amount4'];
$amount5 = $_POST['amount5'];

$DBHelper = new DBHelper();

$DBHelper ->dutchPay5($managerID, $ID1, $ID2, $ID3, $ID4, $ID5, $amount1, $amount2, $amount3, $amount4, $amount5);
?>