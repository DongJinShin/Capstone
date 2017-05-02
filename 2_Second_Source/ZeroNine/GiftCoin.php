<?php
include 'utils/DBHelper.php';

$FromEMail = $_POST['FromEMail'];
$DSTEMail = $_POST['DSTEMail'];
$amount = $_POST['amount'];

$DBHelper = new DBHelper();

$DBHelper ->giftCoin($FromEMail, $DSTEMail, $amount);
?>