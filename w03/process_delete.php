<?php
  $link = mysqli_connect("localhost", "root", "rootroot", "dbp");

  settype($_POST['id'], 'int');

  $filtered = array(
    'id' => mysqli_real_escape_string($link, $_POST['id'])
  );

  $query = "
    DELETE FROM song
    WHERE id ='{$filtered['id']}'
  ";

  $result = mysqli_query($link, $query);

  if ($result == false) {
      echo '삭제하는 과정에서 문제가 발생했습니다. 뒤로 돌아가서 다시 시도하세요.';
      error_log(mysqli_error($link));
  } else {
      echo '성공했습니다. <a href="index.php">메인으로 돌아가기 </a>';
  }
