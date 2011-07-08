<td  class="yellow_box" valign="top">
	<h3>${leftOptionsTitle}</h3>
	<ul>
        <g:each in="${leftOptions}">
        <li id="leftOption${it.id}" name="leftOption${it.id}" class="buttonDiv" onclick="optionClick('leftOption', ${it.id})">${it.name}</li>
              </g:each>
          </ul>                
</td>
      <td width="100px"><img src="${resource(dir:'images/ammrf',file:'space.gif')}" width="20" height="5" /><span class="aligncenter"><h1>${comparisonTitle}</h1></span></td>
<td class="yellow_box" valign="top">
	<h3>${rightOptionsTitle}</h3>
	
	<ul>
        <g:each in="${rightOptions}">
        <li id="rightOption${it.id}" name="rightOption${it.id}" class="buttonDiv" onclick="optionClick('rightOption', ${it.id})">${it.name}</li>
        </g:each>
    </ul> 
</td>