<?php
include 'utils/DBHelper.php';

$FromID = $_POST['FromID'];
$DSTID = $_POST['DSTID'];
$amount = $_POST['amount'];

$DBHelper = new DBHelper();

$DBHelper ->giftCoin($FromID, $DSTID, $amount);

?>