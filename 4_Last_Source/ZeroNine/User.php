<?php
include 'utils/DBHelper.php';

$ID = $_POST['ID'];

$DBHelper = new DBHelper();

$DBHelper ->selectOneUserByStringID($ID);

?>