<?php
class DBHelper
{	
    private $DBHost = "127.0.0.1";
    private $DBUser = "root";
    private $DBPassword = "Password"; //The security issue can not write
    private $DBName = "ninezero";

    function insertUser($EMail, $Password, $name, $QRUri)
    {
		$mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        if(!$mysqli -> query("INSERT INTO nz_user (id_nz_user, nz_user_email, nz_user_password, nz_user_name, nz_user_qr_uri, nz_user_coin) VALUES (null, '$EMail', '$Password', '$name', '$QRUri', 0)"))
        {
            return -1;
        }
        else{
            return $mysqli->insert_id;
        }

        $mysqli -> close();
    }

    function selectOneManagerByEMailPassword($EMail, $password)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT id_nz_manager as managerID, nz_manager_email as EMail, nz_manager_password as password FROM nz_manager WHERE nz_manager_email = '$EMail' and nz_manager_password = '$password'");
        if($result -> num_rows > 0)
        {
            $row = mysqli_fetch_assoc($result);
            echo json_encode($row);
        }
        else
        {
            echo "failed";
        }

        $mysqli -> close();
    }

    function selectTradeList($EMail)
    {

        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        $resultArray = array();

        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT nz_manager.nz_manager_store_name as 'from', nz_save_user_email as DST, nz_save_amount as amount, nz_save_balance as balance, nz_save_time as time FROM nz_save LEFT OUTER JOIN nz_manager on nz_save.nz_save_manager_email = nz_manager.nz_manager_email WHERE nz_save_user_email = '$EMail'");
        while($row = $result->fetch_assoc())
        {
            $row['type'] = 1;
            array_push($resultArray, $row);
        }

        $result = $mysqli -> query("SELECT nz_manager.nz_manager_store_name as 'from', nz_use_user_email as DST, nz_use_amount as amount, nz_use_balance as balance, nz_use_time as time FROM nz_use LEFT OUTER join nz_manager on nz_use.nz_use_manager_email = nz_manager.nz_manager_email WHERE nz_use_user_email = '$EMail'");
        while($row = $result->fetch_assoc())
        {
            $row['type'] = 2;
            array_push($resultArray, $row);
        }

        $result = $mysqli -> query("SELECT nz_user.nz_user_name as 'from', nz_gift_dst_user_email as DST, nz_gift_amount as amount, nz_gift_dst_user_balance as balance, nz_gift_time as time FROM nz_gift LEFT OUTER join nz_user on nz_gift_from_user_email = nz_user.nz_user_email WHERE nz_gift_dst_user_email = '$EMail'");
        while($row = $result->fetch_assoc())
        {
            $row['type'] = 3;
            array_push($resultArray, $row);
        }

        $result = $mysqli -> query("SELECT nz_user.nz_user_name as DST, nz_gift_from_user_email as 'from', nz_gift_amount as amount, nz_gift_from_user_balance as balance, nz_gift_time as time FROM nz_gift LEFT OUTER join nz_user on nz_gift_dst_user_email = nz_user.nz_user_email WHERE nz_gift_from_user_email = '$EMail'");
        while($row = $result->fetch_assoc())
        {
            $row['type'] = 3;
            array_push($resultArray, $row);
        }

        usort($resultArray,function($a,$b) {return strnatcasecmp($b['time'],$a['time']);});

        echo json_encode($resultArray);

        $mysqli -> close();
    }

    function selectOneUserByID($ID)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT id_nz_user as userID, nz_user_email as EMail, nz_user_password as password, nz_user_name as name, nz_user_qr_uri as QRUri, nz_user_coin as coin FROM nz_user WHERE id_nz_user = $ID");
        if($result -> num_rows > 0)
        {
            $row = mysqli_fetch_assoc($result);
            echo json_encode($row);
        }
        else{
            echo "failed";
        }

        $mysqli -> close();
    }

    function selectOneUserByEMail($EMail)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT id_nz_user as userID, nz_user_email as EMail, nz_user_password as password, nz_user_name as name, nz_user_qr_uri as QRUri, nz_user_coin as coin FROM nz_user WHERE nz_user_email = '$EMail'");
        if($result -> num_rows > 0)
        {
            $row = mysqli_fetch_assoc($result);
            echo json_encode($row);
        }
        else{
            echo "failed";
        }

        $mysqli -> close();
    }

    function selectOneUserCoinByEMail($EMail)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_email = '$EMail'");
        if($result -> num_rows > 0)
        {
            $row = mysqli_fetch_assoc($result);
            echo $row['coin'];
        }
        else{
            echo "failed";
        }

        $mysqli -> close();
    }

    function selectOneUserByEMailPassword($EMail, $password)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT id_nz_user as userID, nz_user_email as EMail, nz_user_password as password, nz_user_name as name, nz_user_qr_uri as QRUri, nz_user_coin as coin FROM nz_user WHERE nz_user_email = '$EMail' and nz_user_password = '$password'");
        if($result -> num_rows > 0)
        {
            $row = mysqli_fetch_assoc($result);
            echo json_encode($row);
        }
        else
        {
            echo "failed";
        }

        $mysqli -> close();
    }

    function saveCoin($managerEMail, $userEMail, $amount)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        mysqli_autocommit($mysqli, FALSE);

        mysqli_commit($mysqli);

        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin + $amount WHERE nz_user_email = '$userEMail'"))
        {
            mysqli_rollback($mysqli);
            echo "500";
        }
        else
        {
            $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_email = '$userEMail'");
            if($result -> num_rows > 0)
            {
                date_default_timezone_set("Asia/Seoul");
                $row = mysqli_fetch_assoc($result);
                $balance = $row['coin'];
                $time = date("Y-m-d H:i:s");

                if(!$mysqli -> query("INSERT INTO nz_save (id_nz_save, nz_save_manager_email, nz_save_user_email, nz_save_amount, nz_save_balance, nz_save_time) VALUES (null, '$managerEMail', '$userEMail', $amount, $balance, '$time')"))
                {
                    mysqli_rollback($mysqli);
                    echo "500";
                }
                else
                {
                    mysqli_commit($mysqli);
                    echo "200";
                }
            }
            else
            {
                mysqli_rollback($mysqli);
                echo "500";
            }
        }

        $mysqli -> close();
    }

    function useCoin($managerEMail, $userEMail, $amount)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        mysqli_autocommit($mysqli, FALSE);

        mysqli_commit($mysqli);

        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount WHERE nz_user_email = '$userEMail'"))
        {
            mysqli_rollback($mysqli);
            echo "500";
        }
        else
        {
            $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_email = '$userEMail'");
            if($result -> num_rows > 0)
            {
                $row = mysqli_fetch_assoc($result);
                if($row['coin'] < 0)
                {
                    mysqli_rollback($mysqli);
                    echo "502";
                }
                else
                {
                    date_default_timezone_set("Asia/Seoul");
                    $balance = $row['coin'];
                    $time = date("Y-m-d H:i:s");

                    if(!$mysqli -> query("INSERT INTO nz_use (id_nz_use, nz_use_manager_email, nz_use_user_email, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerEMail', '$userEMail', $amount, $balance, '$time')"))
                    {
                        mysqli_rollback($mysqli);
                        echo "500";
                    }
                    else
                    {
                        mysqli_commit($mysqli);
                        echo "200";
                    }
                }
            }
            else
            {
                mysqli_rollback($mysqli);
                echo "501";
            }
        }

        $mysqli -> close();
    }

    function giftCoin($FromEMail, $DSTEMail, $amount)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        mysqli_autocommit($mysqli, FALSE);

        mysqli_commit($mysqli);

        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount WHERE nz_user_email = '$FromEMail'"))
        {
            mysqli_rollback($mysqli);
            echo "500";
        }
        else
        {
            $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_email = '$FromEMail'");
            if($result -> num_rows > 0)
            {
                $row = mysqli_fetch_assoc($result);
                if($row['coin'] < 0)
                {
                    mysqli_rollback($mysqli);
                    echo "502";
                }
                else
                {
                    $fromUserBalance = $row['coin'];
                    $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_email = '$DSTEMail'");
                    if($result -> num_rows > 0)
                    {
                        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin + $amount WHERE nz_user_email = '$DSTEMail'"))
                        {
                            mysqli_rollback($mysqli);
                            echo "504";
                        }
                        else
                        {
                            date_default_timezone_set("Asia/Seoul");
                            $row = mysqli_fetch_assoc($result);
                            $dstUserBalance = $row['coin'] + $amount;
                            $time = date("Y-m-d H:i:s");

                            if(!$mysqli -> query("INSERT INTO nz_gift (id_nz_gift, nz_gift_from_user_email, nz_gift_dst_user_email, nz_gift_amount, nz_gift_from_user_balance, nz_gift_dst_user_balance, nz_gift_time) VALUES (null, '$FromEMail', '$DSTEMail', $amount, $fromUserBalance, $dstUserBalance, '$time')"))
                            {
                                mysqli_rollback($mysqli);
                                echo "500";
                            }
                            else
                            {
                                mysqli_commit($mysqli);
                                echo "200";
                            }
                        }
                    }
                    else
                    {
                        mysqli_rollback($mysqli);
                        echo "503";
                    }
                }
            }
            else
            {
                mysqli_rollback($mysqli);
                echo "501";
            }
        }

        $mysqli -> close();
    }
}
?>