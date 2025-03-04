<?php

if (preg_match ('/config.php/', basename($_SERVER['PHP_SELF']))) die ('Unable to access this script directly from browser!');

define ("DB_HOST","192.168.196.80");
define ("DB_USER","client");
define ("DB_PASS","rosmayanti");
define ("DB_NAME","siktunggakjati28102018");

$connection = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_NAME);

$db = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
if($db->connect_error){
    die("Unable to connect database: " . $db->connect_error);
}

function escape($string) {
    global $connection;
    return mysqli_real_escape_string($connection, $string);
}

function query($sql) {
    global $connection;
    $query = mysqli_query($connection, $sql);
    confirm($query);
    return $query;
}

function confirm($query) {
    global $connection;
    if(!$query) {
        die("Query failed! " . mysqli_error($connection));
    }
}

function fetch_array($result) {
    return mysqli_fetch_array($result);
}

function fetch_assoc($result) {
    return mysqli_fetch_assoc($result);
}

function num_rows($result) {
    return mysqli_num_rows($result);
}

function insertTable($table_name, $insertvalue="") {
  $query1 = "";
  $query2 = "";
  if($insertvalue != ""){
    $i=0;
    foreach($insertvalue as $key => $item) {
      if($i == 0) {
        $query1 = $key;
        $query2 = "'".$item."'";
      }
      else{
        $query1 = $query1 . ", ".$key;
        $query2 = $query2 . ", '".$item."'";
      }
      $i++;
    }
  }

  $query = "INSERT INTO ".$table_name." (".$query1.") VALUES (".$query2.")";
  query($query);
}

// Get settings
$getSettings = query("SELECT * FROM setting");
$dataSettings = fetch_assoc($getSettings);

// Get date and time
date_default_timezone_set('Asia/Makassar');
$tanggal    = date('d');
$bulan      = date('m');
$tahun      = date('Y');
$month      = date('Y-m');
$date       = date('Y-m-d');
$time       = date('H:i:s');
$date_time  = date('Y-m-d H:i:s');

header('Content-Type: text/html; charset=utf-8');

?>
