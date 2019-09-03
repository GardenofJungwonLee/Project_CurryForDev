downExcel = num => {
    $("#level").table2excel({
      exclude: ".excludeThisClass",
      name: "Level " + num,
      filename: "level" + num + ".xls",
      });
  }

makeTable = num => {
  $.getJSON("assets/json/level" + num + ".json", function(data) {
    $.each(data, function(I, item) {
      let name = `<tr><td lang="ko"><strong><a style="color: #ffffff" target="_blank" href="${item.url}">${item.name}</a></strong></td>`;
      let category = `<td lang="ko">${item.category}</td>`;
      let success = `<td lang="ko">${item.success}명</td>`;
      let q = `https://www.google.com/search?q=${item.name}+프로그래머스+JAVA`;
      let expl =
        '<td><a target="_blank" href="' +
        q +
        '" class="button alt small icon fa-search">WEB</a></td></tr>';
      let tuple = name + category + success + expl;
      document.getElementById("contents").innerHTML =
        document.getElementById("contents").innerHTML + tuple;
    });
  });
}