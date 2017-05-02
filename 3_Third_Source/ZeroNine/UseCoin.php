<?php
include 'utils/DBHelper.php';

$managerEMail = $_POST['managerEMail'];
$userEMail = $_POST['userEMail'];
$amount = $_POST['amount'];

$DBHelper = new DBHelper();

$DBHelper ->useCoin($managerEMail, $userEMail, $amount);
?>