<?php
include 'utils/DBHelper.php';

$managerID = $_POST['managerID'];
$ID1 = $_POST['ID1'];
$ID2 = $_POST['ID2'];
$ID3 = $_POST['ID3'];
$amount1 = $_POST['amount1'];
$amount2 = $_POST['amount2'];
$amount3 = $_POST['amount3'];

$DBHelper = new DBHelper();

$DBHelper ->dutchPay3($managerID, $ID1, $ID2, $ID3, $amount1, $amount2, $amount3);
?>