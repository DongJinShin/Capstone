<?php
class QRHelper
{
    function makeDataUri($datas)
    {
        $uri = 'http://chart.apis.google.com/chart?';
        $query = "";
        foreach($datas as $key => $val)
        {
            if( strcmp($query, "") != 0 )
            {
                $query .= "&";
            }
            $query .= "$key=$val";
        }
        $uri .= $query;
        return $uri;
    }

    function makeQRUri($ID)
    {
        if (is_null($ID))
        {
            return null;
        }
        //$url = rawurlencode($EMail);
        $datas = array("cht" => "qr", "chs" => "300x300", "choe" => "Shift_JIS", "chl" => $ID);
        $qrUri = $this -> makeDataUri($datas);
        return $qrUri;
    }
}
?>