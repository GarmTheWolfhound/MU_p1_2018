<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Page Title</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
</form>
    <?php
    $hostname = "localhost";
    $username = "root";
    $password = "";
    $dbname = 'image_db';
    $dbh = new PDO("mysql:host=$hostname;dbname=$dbname", $username, $password);; // connect to the database
    if(isset($_POST['btn'])){ // checks if button has been clicked--- run code if true
        $name =  $_POST['name']; // save file name in variable name
        $data = file_get_contents($_FILES['myfile']['tmp_name']); // save file in variable data

        $query = $dbh->prepare("insert into myblob values('',?,?)"); // insert name and file into database
        $query->bindParam(1,$name); // bind name to param 1
        $query->bindParam(2,$data); // bind data to param 2
        $query->execute();  // execute query
}

    ?>
    <div class="container">
    <?php
$stat = $dbh->prepare("select * from myblob"); // select all from database
$stat->execute();
while($row = $stat->fetch()){ // loop through result from query and display it
    echo "<div class='card'>";
    echo "<h3>".$row['name']."</h3>";
    echo '<img src="data:image/jpeg;base64,'.base64_encode( $row['data'] ).'"/>';
    echo "</div>";
}
?>
</div>
<div class="form">
    <form method="post" enctype="multipart/form-data"> <!-- html code for markup -->
        <input type="text" name="name" placeholder="enter a title"><br/>
        <input type="file" name="myfile"><br/>
        <input type="submit" name="btn" value="Upload file" />
    </form>
    </div>

</body>
</html>