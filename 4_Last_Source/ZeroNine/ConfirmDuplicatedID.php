<?php
include 'utils/DBHelper.php';

$ID = $_POST['ID'];

$DBHelper = new DBHelper();

$lastInsertID = $DBHelper -> confirmDuplicatedID($ID);

?>