import React, { useState } from 'react';

import * as FaIcons from 'react-icons/fa';
import * as AiIcons from 'react-icons/ai';
import { SidebarData } from './SidemenuData';

import { Link } from 'react-router-dom';

function Navbar() {
    const [sidebar, setSidebar] = useState(false);
  
    const showSidebar = () => setSidebar(!sidebar);
  
    return (
   <h1>s</h1>
    );
  }
  
  export default Navbar;
