<?php
include 'utils/DBHelper.php';

$managerID = $_POST['managerID'];
$userID = $_POST['userID'];
$amount = $_POST['amount'];

$DBHelper = new DBHelper();

$DBHelper ->saveCoin($managerID, $userID, $amount);
?>