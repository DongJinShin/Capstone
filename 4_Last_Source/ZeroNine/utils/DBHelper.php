<?php
class DBHelper
{
    private $DBHost = "127.0.0.1";
    private $DBUser = "root";
    private $DBPassword = "Password"; //The security issue can not write
    private $DBName = "ninezero";

    function send_notification($tokens, $message)
    {
        $url = 'https://fcm.googleapis.com/fcm/send';
        $fields = array (
            'registration_ids' => $tokens,
            'data' => $message
        );
        $headers = array (
            'Authorization: key=' . "AAAA77icTQY:APA91bFBHanC2rHfB3vVsLbOZTrzHpsGTnYInTjiYCbQznLhJS7xH6gpa54t8vLx31cnuoSuWKwS5Aw7Wz9rvM1pIlgKHFJAuZiKZyphaPLBF2FPTZUwkq_xqOI7hhbHvlwqbhh9UQ3h ",
            'Content-Type: application/json'
        );

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
        $result = curl_exec($ch);
        if ($result === FALSE) {
            die('Curl failed: ' . curl_error($ch));
        }
        curl_close($ch);
        return $result;
    }

    function insertUser($ID, $EMail, $Password, $name, $QRUri)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        if(!$mysqli -> query("INSERT INTO nz_user (id_nz_user, nz_user_id, nz_user_email, nz_user_password, nz_user_name, nz_user_qr_uri, nz_user_coin) VALUES (null, '$ID', '$EMail', '$Password', '$name', '$QRUri', 0)"))
        {
            return -1;
        }
        else{
            return $mysqli->insert_id;
        }

        $mysqli -> close();
    }


    function insertFCM($registrationID, $deviceID)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT * FROM nz_fcm_register WHERE nz_fcm_register_device_id = '$deviceID'");
        if($result -> num_rows > 0)
        {
            if(!$mysqli -> query("UPDATE nz_fcm_register SET nz_fcm_register_id = '$registrationID' WHERE nz_fcm_register_device_id = '$deviceID'"))
            {
                return -1;
            }
            else
            {
                return 1;
            }
        }
        else
        {
            if(!$mysqli -> query("INSERT INTO nz_fcm_register (id_nz_fcm_register, nz_fcm_register_id, nz_fcm_register_device_id) VALUES (null, '$registrationID', '$deviceID')"))
            {
                return -1;
            }
            else{
                return $mysqli->insert_id;
            }
        }

        $mysqli -> close();
    }

    function insertFCMID($ID, $deviceID)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT * FROM nz_fcm_register_id WHERE nz_fcm_register_id_device_id = '$deviceID'");
        if($result -> num_rows > 0)
        {
            if(!$mysqli -> query("UPDATE nz_fcm_register_id SET nz_fcm_register_id_id = '$ID' WHERE nz_fcm_register_id_device_id = '$deviceID'"))
            {
                return 1;
            }
            else
            {
                return -1;
            }
        }
        else
        {
            if(!$mysqli -> query("INSERT INTO nz_fcm_register_id (id_nz_fcm_register_id, nz_fcm_register_id_id, nz_fcm_register_id_device_id) VALUES (null, '$ID', '$deviceID')"))
            {
                return -1;
            }
            else{
                return $mysqli->insert_id;
            }
        }


        $mysqli -> close();
    }


    function confirmDuplicatedID($ID)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT * FROM nz_user WHERE nz_user_id = '$ID'");
        if($result -> num_rows > 0)
        {
            http_response_code(500);
        }
        else
        {
            http_response_code(200);
        }

        $mysqli -> close();
    }


    function selectOneManagerByIDPassword($ID, $password)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT id_nz_manager as managerID, nz_manager_id as ID, nz_manager_email as EMail, nz_manager_password as password FROM nz_manager WHERE nz_manager_id = '$ID' and nz_manager_password = '$password'");
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

    function selectTradeList($ID)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        $resultArray = array();

        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT nz_manager.nz_manager_store_name as 'from', nz_save_user_id as DST, nz_save_amount as amount, nz_save_balance as balance, nz_save_time as time FROM nz_save LEFT OUTER JOIN nz_manager on nz_save.nz_save_manager_id = nz_manager.nz_manager_id WHERE nz_save_user_id = '$ID'");
        while($row = $result->fetch_assoc())
        {
            $row['type'] = 1;
            array_push($resultArray, $row);
        }

        $result = $mysqli -> query("SELECT nz_manager.nz_manager_store_name as 'from', nz_use_user_id as DST, nz_use_amount as amount, nz_use_balance as balance, nz_use_time as time FROM nz_use LEFT OUTER join nz_manager on nz_use.nz_use_manager_id = nz_manager.nz_manager_id WHERE nz_use_user_id = '$ID'");
        while($row = $result->fetch_assoc())
        {
            $row['type'] = 2;
            array_push($resultArray, $row);
        }

        $result = $mysqli -> query("SELECT nz_user.nz_user_id as 'from', nz_gift_dst_user_id as DST, nz_gift_amount as amount, nz_gift_dst_user_balance as balance, nz_gift_time as time FROM nz_gift LEFT OUTER join nz_user on nz_gift_from_user_id = nz_user.nz_user_id WHERE nz_gift_dst_user_id = '$ID'");
        while($row = $result->fetch_assoc())
        {
            $row['type'] = 3;
            array_push($resultArray, $row);
        }

        $result = $mysqli -> query("SELECT nz_user.nz_user_id as DST, nz_gift_from_user_id as 'from', nz_gift_amount as amount, nz_gift_from_user_balance as balance, nz_gift_time as time FROM nz_gift LEFT OUTER join nz_user on nz_gift_dst_user_id = nz_user.nz_user_id WHERE nz_gift_from_user_id = '$ID'");
        while($row = $result->fetch_assoc())
        {
            $row['type'] = 3;
            array_push($resultArray, $row);
        }

        usort($resultArray,function($a,$b) { if($a['time'] == $b['time']) return 0; return ($b['time'] < $a['time']) ? -1 : 1;});

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

    function selectOneUserByStringID($ID)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT id_nz_user as userID, nz_user_id as ID, nz_user_email as EMail, nz_user_password as password, nz_user_name as name, nz_user_qr_uri as QRUri, nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID'");
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

    function selectOneUserCoinByID($ID)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID'");
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

    function selectOneUserByIDPassword($ID, $password)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT id_nz_user as userID, nz_user_id as ID, nz_user_email as EMail, nz_user_password as password, nz_user_name as name, nz_user_qr_uri as QRUri, nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID' and nz_user_password = '$password'");
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

    function selectNotice()
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        $resultArray = array();

        if (mysqli_connect_errno())
        {
            exit();
        }

        $result = $mysqli -> query("SELECT id_nz_notice as noticeID, nz_notice_title as title, nz_notice_content as content, nz_notice_time as time FROM nz_notice ORDER  BY time DESC");
        if($result -> num_rows > 0)
        {
            while($row = $result->fetch_assoc())
            {
                array_push($resultArray, $row);
            }

            echo json_encode($resultArray);
        }
        else
        {
            echo "failed";
        }

        $mysqli -> close();
    }

    function saveCoin($managerID, $userID, $amount)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        mysqli_autocommit($mysqli, FALSE);

        mysqli_commit($mysqli);

        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin + $amount WHERE nz_user_id = '$userID'"))
        {
            mysqli_rollback($mysqli);
            echo "500";
        }
        else
        {
            $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$userID'");
            if($result -> num_rows > 0)
            {
                date_default_timezone_set("Asia/Seoul");
                $row = mysqli_fetch_assoc($result);
                $balance = $row['coin'];
                $time = date("Y-m-d H:i:s");

                if(!$mysqli -> query("INSERT INTO nz_save (id_nz_save, nz_save_manager_id, nz_save_user_id, nz_save_amount, nz_save_balance, nz_save_time) VALUES (null, '$managerID', '$userID', $amount, $balance, '$time')"))
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

    function useCoin($managerID, $userID, $amount)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        mysqli_autocommit($mysqli, FALSE);

        mysqli_commit($mysqli);

        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount WHERE nz_user_id = '$userID'"))
        {
            mysqli_rollback($mysqli);
            echo "500";
        }
        else
        {
            $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$userID'");
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

                    if(!$mysqli -> query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$userID', $amount, $balance, '$time')"))
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



    function dutchPay2($managerID, $ID1, $ID2, $amount1, $amount2)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        mysqli_autocommit($mysqli, FALSE);

        mysqli_commit($mysqli);

        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount1 WHERE nz_user_id = '$ID1'"))
        {
            mysqli_rollback($mysqli);
            echo "500";
        }
        else
        {
            $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID1'");
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
                    $balance1 = $row['coin'];

                    if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount2 WHERE nz_user_id = '$ID2'"))
                    {
                        mysqli_rollback($mysqli);
                        echo "500";
                    }
                    else {
                        $result = $mysqli->query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID2'");
                        if ($result->num_rows > 0)
                        {
                            $row = mysqli_fetch_assoc($result);
                            if ($row['coin'] < 0)
                            {
                                mysqli_rollback($mysqli);
                                echo "502";
                            }
                            else
                            {
                                date_default_timezone_set("Asia/Seoul");
                                $balance2 = $row['coin'];
                                $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s"), date("m"), date("d"), date("Y")));

                                if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID1', $amount1, $balance1, '$time')"))
                                {
                                    mysqli_rollback($mysqli);
                                    echo "500";
                                }
                                else
                                {
                                    $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s") + 1, date("m"), date("d"), date("Y")));
                                    if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID2', $amount2, $balance2, '$time')"))
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
                        }
                        else
                        {
                            mysqli_rollback($mysqli);
                            echo "501";
                        }
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



    function dutchPay3($managerID, $ID1, $ID2, $ID3, $amount1, $amount2, $amount3)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        mysqli_autocommit($mysqli, FALSE);

        mysqli_commit($mysqli);

        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount1 WHERE nz_user_id = '$ID1'"))
        {
            mysqli_rollback($mysqli);
            echo "500";
        }
        else
        {
            $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID1'");
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
                    $balance1 = $row['coin'];

                    if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount2 WHERE nz_user_id = '$ID2'"))
                    {
                        mysqli_rollback($mysqli);
                        echo "500";
                    }
                    else
                    {
                        $result = $mysqli->query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID2'");
                        if ($result->num_rows > 0)
                        {
                            $row = mysqli_fetch_assoc($result);
                            if ($row['coin'] < 0)
                            {
                                mysqli_rollback($mysqli);
                                echo "502";
                            }
                            else
                            {
                                $balance2 = $row['coin'];

                                if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount3 WHERE nz_user_id = '$ID3'"))
                                {
                                    mysqli_rollback($mysqli);
                                    echo "500";
                                }
                                else
                                {
                                    $result = $mysqli->query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID3'");
                                    if ($result->num_rows > 0)
                                    {
                                        $row = mysqli_fetch_assoc($result);
                                        if ($row['coin'] < 0)
                                        {
                                            mysqli_rollback($mysqli);
                                            echo "502";
                                        }
                                        else
                                        {
                                            date_default_timezone_set("Asia/Seoul");
                                            $balance3 = $row['coin'];
                                            $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s"), date("m"), date("d"), date("Y")));

                                            if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID1', $amount1, $balance1, '$time')"))
                                            {
                                                mysqli_rollback($mysqli);
                                                echo "500";
                                            }
                                            else
                                            {
                                                $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s") + 1, date("m"), date("d"), date("Y")));
                                                if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID2', $amount2, $balance2, '$time')"))
                                                {
                                                    mysqli_rollback($mysqli);
                                                    echo "500";
                                                }
                                                else
                                                {
                                                    $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s") + 2, date("m"), date("d"), date("Y")));
                                                    if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID3', $amount3, $balance3, '$time')"))
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
                                        }
                                    }
                                    else
                                    {
                                        mysqli_rollback($mysqli);
                                        echo "501";
                                    }
                                }
                            }
                        }
                        else
                        {
                            mysqli_rollback($mysqli);
                            echo "501";
                        }
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



    function dutchPay4($managerID, $ID1, $ID2, $ID3, $ID4, $amount1, $amount2, $amount3, $amount4)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        mysqli_autocommit($mysqli, FALSE);

        mysqli_commit($mysqli);

        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount1 WHERE nz_user_id = '$ID1'"))
        {
            mysqli_rollback($mysqli);
            echo "500";
        }
        else
        {
            $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID1'");
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
                    $balance1 = $row['coin'];

                    if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount2 WHERE nz_user_id = '$ID2'"))
                    {
                        mysqli_rollback($mysqli);
                        echo "500";
                    }
                    else
                    {
                        $result = $mysqli->query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID2'");
                        if ($result->num_rows > 0)
                        {
                            $row = mysqli_fetch_assoc($result);
                            if ($row['coin'] < 0)
                            {
                                mysqli_rollback($mysqli);
                                echo "502";
                            }
                            else
                            {
                                $balance2 = $row['coin'];

                                if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount3 WHERE nz_user_id = '$ID3'"))
                                {
                                    mysqli_rollback($mysqli);
                                    echo "500";
                                }
                                else
                                {
                                    $result = $mysqli->query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID3'");
                                    if ($result->num_rows > 0)
                                    {
                                        $row = mysqli_fetch_assoc($result);
                                        if ($row['coin'] < 0)
                                        {
                                            mysqli_rollback($mysqli);
                                            echo "502";
                                        }
                                        else
                                        {
                                            $balance3 = $row['coin'];

                                            if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount4 WHERE nz_user_id = '$ID4'"))
                                            {
                                                mysqli_rollback($mysqli);
                                                echo "500";
                                            }
                                            else
                                            {
                                                $result = $mysqli->query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID4'");
                                                if ($result->num_rows > 0)
                                                {
                                                    $row = mysqli_fetch_assoc($result);
                                                    if ($row['coin'] < 0)
                                                    {
                                                        mysqli_rollback($mysqli);
                                                        echo "502";
                                                    }
                                                    else
                                                    {
                                                        date_default_timezone_set("Asia/Seoul");
                                                        $balance4 = $row['coin'];
                                                        $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s"), date("m"), date("d"), date("Y")));

                                                        if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID1', $amount1, $balance1, '$time')"))
                                                        {
                                                            mysqli_rollback($mysqli);
                                                            echo "500";
                                                        }
                                                        else
                                                        {
                                                            $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s") + 1, date("m"), date("d"), date("Y")));
                                                            if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID2', $amount2, $balance2, '$time')"))
                                                            {
                                                                mysqli_rollback($mysqli);
                                                                echo "500";
                                                            }
                                                            else
                                                            {
                                                                $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s") + 2, date("m"), date("d"), date("Y")));
                                                                if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID3', $amount3, $balance3, '$time')"))
                                                                {
                                                                    mysqli_rollback($mysqli);
                                                                    echo "500";
                                                                }
                                                                else
                                                                {
                                                                    $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s") + 3, date("m"), date("d"), date("Y")));
                                                                    if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID4', $amount4, $balance4, '$time')"))
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
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    mysqli_rollback($mysqli);
                                                    echo "501";
                                                }
                                            }
                                        }
                                    }
                                    else
                                    {
                                        mysqli_rollback($mysqli);
                                        echo "501";
                                    }
                                }
                            }
                        }
                        else
                        {
                            mysqli_rollback($mysqli);
                            echo "501";
                        }
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


    function dutchPay5($managerID, $ID1, $ID2, $ID3, $ID4, $ID5, $amount1, $amount2, $amount3, $amount4, $amount5)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        mysqli_autocommit($mysqli, FALSE);

        mysqli_commit($mysqli);

        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount1 WHERE nz_user_id = '$ID1'"))
        {
            mysqli_rollback($mysqli);
            echo "500";
        }
        else
        {
            $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID1'");
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
                    $balance1 = $row['coin'];

                    if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount2 WHERE nz_user_id = '$ID2'"))
                    {
                        mysqli_rollback($mysqli);
                        echo "500";
                    }
                    else
                    {
                        $result = $mysqli->query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID2'");
                        if ($result->num_rows > 0)
                        {
                            $row = mysqli_fetch_assoc($result);
                            if ($row['coin'] < 0)
                            {
                                mysqli_rollback($mysqli);
                                echo "502";
                            }
                            else
                            {
                                $balance2 = $row['coin'];

                                if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount3 WHERE nz_user_id = '$ID3'"))
                                {
                                    mysqli_rollback($mysqli);
                                    echo "500";
                                }
                                else
                                {
                                    $result = $mysqli->query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID3'");
                                    if ($result->num_rows > 0)
                                    {
                                        $row = mysqli_fetch_assoc($result);
                                        if ($row['coin'] < 0)
                                        {
                                            mysqli_rollback($mysqli);
                                            echo "502";
                                        }
                                        else
                                        {
                                            $balance3 = $row['coin'];

                                            if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount4 WHERE nz_user_id = '$ID4'"))
                                            {
                                                mysqli_rollback($mysqli);
                                                echo "500";
                                            }
                                            else
                                            {
                                                $result = $mysqli->query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID4'");
                                                if ($result->num_rows > 0)
                                                {
                                                    $row = mysqli_fetch_assoc($result);
                                                    if ($row['coin'] < 0)
                                                    {
                                                        mysqli_rollback($mysqli);
                                                        echo "502";
                                                    }
                                                    else
                                                    {
                                                        $balance4 = $row['coin'];

                                                        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount5 WHERE nz_user_id = '$ID5'"))
                                                        {
                                                            mysqli_rollback($mysqli);
                                                            echo "500";
                                                        }
                                                        else
                                                        {
                                                            $result = $mysqli->query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID5'");
                                                            if ($result->num_rows > 0)
                                                            {
                                                                $row = mysqli_fetch_assoc($result);
                                                                if ($row['coin'] < 0)
                                                                {
                                                                    mysqli_rollback($mysqli);
                                                                    echo "502";
                                                                }
                                                                else
                                                                {
                                                                    date_default_timezone_set("Asia/Seoul");
                                                                    $balance5 = $row['coin'];
                                                                    $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s"), date("m"), date("d"), date("Y")));

                                                                    if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID1', $amount1, $balance1, '$time')"))
                                                                    {
                                                                        mysqli_rollback($mysqli);
                                                                        echo "500";
                                                                    }
                                                                    else
                                                                    {
                                                                        $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s") + 1, date("m"), date("d"), date("Y")));
                                                                        if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID2', $amount2, $balance2, '$time')"))
                                                                        {
                                                                            mysqli_rollback($mysqli);
                                                                            echo "500";
                                                                        }
                                                                        else
                                                                        {
                                                                            $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s") + 2, date("m"), date("d"), date("Y")));
                                                                            if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID3', $amount3, $balance3, '$time')"))
                                                                            {
                                                                                mysqli_rollback($mysqli);
                                                                                echo "500";
                                                                            }
                                                                            else
                                                                            {
                                                                                $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s") + 3, date("m"), date("d"), date("Y")));
                                                                                if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID4', $amount4, $balance4, '$time')"))
                                                                                {
                                                                                    mysqli_rollback($mysqli);
                                                                                    echo "500";
                                                                                }
                                                                                else
                                                                                {
                                                                                    $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s") + 4, date("m"), date("d"), date("Y")));
                                                                                    if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID5', $amount5, $balance5, '$time')"))
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
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            else
                                                            {
                                                                mysqli_rollback($mysqli);
                                                                echo "501";
                                                            }
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    mysqli_rollback($mysqli);
                                                    echo "501";
                                                }
                                            }
                                        }
                                    }
                                    else
                                    {
                                        mysqli_rollback($mysqli);
                                        echo "501";
                                    }
                                }
                            }
                        }
                        else
                        {
                            mysqli_rollback($mysqli);
                            echo "501";
                        }
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

    function dutchPay6($managerID, $ID1, $ID2, $ID3, $ID4, $ID5, $ID6, $amount1, $amount2, $amount3, $amount4, $amount5, $amount6)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        mysqli_autocommit($mysqli, FALSE);

        mysqli_commit($mysqli);

        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount1 WHERE nz_user_id = '$ID1'"))
        {
            mysqli_rollback($mysqli);
            echo "500";
        }
        else
        {
            $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID1'");
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
                    $balance1 = $row['coin'];

                    if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount2 WHERE nz_user_id = '$ID2'"))
                    {
                        mysqli_rollback($mysqli);
                        echo "500";
                    }
                    else
                    {
                        $result = $mysqli->query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID2'");
                        if ($result->num_rows > 0)
                        {
                            $row = mysqli_fetch_assoc($result);
                            if ($row['coin'] < 0)
                            {
                                mysqli_rollback($mysqli);
                                echo "502";
                            }
                            else
                            {
                                $balance2 = $row['coin'];

                                if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount3 WHERE nz_user_id = '$ID3'"))
                                {
                                    mysqli_rollback($mysqli);
                                    echo "500";
                                }
                                else
                                {
                                    $result = $mysqli->query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID3'");
                                    if ($result->num_rows > 0)
                                    {
                                        $row = mysqli_fetch_assoc($result);
                                        if ($row['coin'] < 0)
                                        {
                                            mysqli_rollback($mysqli);
                                            echo "502";
                                        }
                                        else
                                        {
                                            $balance3 = $row['coin'];

                                            if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount4 WHERE nz_user_id = '$ID4'"))
                                            {
                                                mysqli_rollback($mysqli);
                                                echo "500";
                                            }
                                            else
                                            {
                                                $result = $mysqli->query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID4'");
                                                if ($result->num_rows > 0)
                                                {
                                                    $row = mysqli_fetch_assoc($result);
                                                    if ($row['coin'] < 0)
                                                    {
                                                        mysqli_rollback($mysqli);
                                                        echo "502";
                                                    }
                                                    else
                                                    {
                                                        $balance4 = $row['coin'];

                                                        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount5 WHERE nz_user_id = '$ID5'"))
                                                        {
                                                            mysqli_rollback($mysqli);
                                                            echo "500";
                                                        }
                                                        else
                                                        {
                                                            $result = $mysqli->query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID5'");
                                                            if ($result->num_rows > 0)
                                                            {
                                                                $row = mysqli_fetch_assoc($result);
                                                                if ($row['coin'] < 0)
                                                                {
                                                                    mysqli_rollback($mysqli);
                                                                    echo "502";
                                                                }
                                                                else
                                                                {
                                                                    $balance5 = $row['coin'];

                                                                    if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount6 WHERE nz_user_id = '$ID6'"))
                                                                    {
                                                                        mysqli_rollback($mysqli);
                                                                        echo "500";
                                                                    }
                                                                    else
                                                                    {
                                                                        $result = $mysqli->query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$ID6'");
                                                                        if ($result->num_rows > 0)
                                                                        {
                                                                            $row = mysqli_fetch_assoc($result);
                                                                            if ($row['coin'] < 0)
                                                                            {
                                                                                mysqli_rollback($mysqli);
                                                                                echo "502";
                                                                            }
                                                                            else
                                                                            {
                                                                                date_default_timezone_set("Asia/Seoul");
                                                                                $balance6 = $row['coin'];
                                                                                $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s"), date("m"), date("d"), date("Y")));

                                                                                if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID1', $amount1, $balance1, '$time')"))
                                                                                {
                                                                                    mysqli_rollback($mysqli);
                                                                                    echo "500";
                                                                                }
                                                                                else
                                                                                {
                                                                                    $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s") + 1, date("m"), date("d"), date("Y")));
                                                                                    if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID2', $amount2, $balance2, '$time')"))
                                                                                    {
                                                                                        mysqli_rollback($mysqli);
                                                                                        echo "500";
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s") + 2, date("m"), date("d"), date("Y")));
                                                                                        if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID3', $amount3, $balance3, '$time')"))
                                                                                        {
                                                                                            mysqli_rollback($mysqli);
                                                                                            echo "500";
                                                                                        }
                                                                                        else
                                                                                        {
                                                                                            $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s") + 3, date("m"), date("d"), date("Y")));
                                                                                            if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID4', $amount4, $balance4, '$time')"))
                                                                                            {
                                                                                                mysqli_rollback($mysqli);
                                                                                                echo "500";
                                                                                            }
                                                                                            else
                                                                                            {
                                                                                                $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s") + 4, date("m"), date("d"), date("Y")));
                                                                                                if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID5', $amount5, $balance5, '$time')"))
                                                                                                {
                                                                                                    mysqli_rollback($mysqli);
                                                                                                    echo "500";
                                                                                                }
                                                                                                else
                                                                                                {
                                                                                                    $time = date("Y-m-d H:i:s", mktime(date("H"), date("i"), date("s") + 5, date("m"), date("d"), date("Y")));
                                                                                                    if (!$mysqli->query("INSERT INTO nz_use (id_nz_use, nz_use_manager_id, nz_use_user_id, nz_use_amount, nz_use_balance, nz_use_time) VALUES (null, '$managerID', '$ID6', $amount6, $balance6, '$time')"))
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
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                        else
                                                                        {
                                                                            mysqli_rollback($mysqli);
                                                                            echo "501";
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            else
                                                            {
                                                                mysqli_rollback($mysqli);
                                                                echo "501";
                                                            }
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    mysqli_rollback($mysqli);
                                                    echo "501";
                                                }
                                            }
                                        }
                                    }
                                    else
                                    {
                                        mysqli_rollback($mysqli);
                                        echo "501";
                                    }
                                }
                            }
                        }
                        else
                        {
                            mysqli_rollback($mysqli);
                            echo "501";
                        }
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




    function giftCoin($FromID, $DSTID, $amount)
    {
        $mysqli = new mysqli($this -> DBHost, $this -> DBUser, $this ->DBPassword, $this -> DBName);

		mysqli_set_charset($mysqli,"utf8");
		
        if (mysqli_connect_errno())
        {
            exit();
        }

        mysqli_autocommit($mysqli, FALSE);

        mysqli_commit($mysqli);

        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin - $amount WHERE nz_user_id = '$FromID'"))
        {
            mysqli_rollback($mysqli);
            echo "500";
        }
        else
        {
            $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$FromID'");
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
                    $result = $mysqli -> query("SELECT nz_user_coin as coin FROM nz_user WHERE nz_user_id = '$DSTID'");
                    if($result -> num_rows > 0)
                    {
                        if(!$mysqli -> query("UPDATE nz_user SET nz_user_coin = nz_user_coin + $amount WHERE nz_user_id = '$DSTID'"))
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

                            if(!$mysqli -> query("INSERT INTO nz_gift (id_nz_gift, nz_gift_from_user_id, nz_gift_dst_user_id, nz_gift_amount, nz_gift_from_user_balance, nz_gift_dst_user_balance, nz_gift_time) VALUES (null, '$FromID', '$DSTID', $amount, $fromUserBalance, $dstUserBalance, '$time')"))
                            {
                                mysqli_rollback($mysqli);
                                echo "500";
                            }
                            else
                            {
                                mysqli_commit($mysqli);

                                echo "200";

                                $result = $mysqli -> query("SELECT nz_fcm_register.nz_fcm_register_id as token FROM nz_fcm_register LEFT OUTER JOIN nz_fcm_register_id on nz_fcm_register.nz_fcm_register_device_id = nz_fcm_register_id.nz_fcm_register_id_device_id WHERE nz_fcm_register_id.nz_fcm_register_id_id = '$DSTID'");

                                $tokens = array();

                                if(mysqli_num_rows($result) > 0 )
                                {
                                    while ($row = mysqli_fetch_assoc($result))
                                    {
                                        $tokens[] = $row["token"];
                                    }
                                }

                                $myMessage = $FromID . "님이 " . $amount . " coin 을 선물하였습니다.";
                                $myTitle = "선물 도착";
                                $message = array("title" => $myTitle, "message" => $myMessage, "userID" => $DSTID);


                                $this->send_notification($tokens, $message);
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